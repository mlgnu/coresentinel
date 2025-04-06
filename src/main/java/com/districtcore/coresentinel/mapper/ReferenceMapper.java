package com.districtcore.coresentinel.mapper;

import com.districtcore.coresentinel.model.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NonNull;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {

    @PersistenceContext
    private EntityManager entityManager;

    public <T extends BaseEntity> T map(@NonNull final String id, @TargetType Class<T> type) {
        return entityManager.getReference(type, Long.parseLong(id.substring(1)));
    }

    public <T extends BaseEntity> T map(@NonNull final Long id, @TargetType Class<T> type) {
        return entityManager.getReference(type, id);
    }

    public <T extends BaseEntity> T map(@NonNull final Integer id, @TargetType Class<T> type) {
        return entityManager.getReference(type, id);
    }
}
