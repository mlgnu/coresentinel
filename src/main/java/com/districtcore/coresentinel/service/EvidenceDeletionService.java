package com.districtcore.coresentinel.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

@Service
public class EvidenceDeletionService {
    ConcurrentHashMap<String, CopyOnWriteArrayList<SseEmitter>>
            emitters = new ConcurrentHashMap<>();

    private final ScheduledExecutorService executor
            = Executors.newSingleThreadScheduledExecutor();

    public void registerEmitter(String deletionId, SseEmitter emitter) {
        emitters.compute(deletionId, (key, list) -> {
            if (list == null) {
                list = new CopyOnWriteArrayList<>();
            }
            list.add(emitter);
            return list;
        });

        emitter.onCompletion(() -> removeEmitter(deletionId, emitter));
        emitter.onTimeout(() -> removeEmitter(deletionId, emitter));

        executor.scheduleAtFixedRate(() -> {
            sendHeartbeat(deletionId, emitter);
        }, 15, 15, TimeUnit.SECONDS);
    }

    private void removeEmitter(String deletionId, SseEmitter emitter) {
        emitters.computeIfPresent(deletionId, (key, list) -> {
            list.remove(emitter);
            return list.isEmpty() ? null : list;
        });
    }

    public void sendStatus(String deletionId, String status) {
        List<SseEmitter> emitters = this.emitters.get(deletionId);
        if (emitters != null) {
            for (SseEmitter emitter : emitters) {
                try {
                    emitter.send(SseEmitter.event()
                            .data(status)
                            .id(deletionId)
                            .name("DELETION_STATUS")
                    );
                } catch (IOException ex) {
                    emitter.completeWithError(ex);
                }
            }
        }
    }

    private void sendHeartbeat(String deletionId, SseEmitter emitter) {
        try {
            emitter.send(SseEmitter.event().comment("ping"));
        } catch (IOException ignored) {}
    }
}
