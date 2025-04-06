package com.districtcore.coresentinel.annotation;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {
    private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();

    @Around("@annotation(com.districtcore.coresentinel.annotation.RateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }
        String clientIp = attributes.getRequest().getRemoteAddr();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimitAnnotation = method.getAnnotation(RateLimit.class);

        Bucket bucket = buckets.computeIfAbsent(clientIp, key -> createNewBucket(rateLimitAnnotation));

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            HttpServletResponse response = attributes.getResponse();
            if (response != null) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.getWriter().write("Too many requests, rate limit exceeded");
                response.getWriter().flush();
            }
            return null;
        }
    }

    private Bucket createNewBucket(RateLimit rateLimitAnnotation) {
        int capacity = rateLimitAnnotation.capacity();
        int refillPeriod = rateLimitAnnotation.refillPeriod();
        Bandwidth limit = Bandwidth.simple(capacity, Duration.ofSeconds(refillPeriod));
        return Bucket.builder().addLimit(limit).build();
    }
}