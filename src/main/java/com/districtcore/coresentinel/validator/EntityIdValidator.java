package com.districtcore.coresentinel.validator;

import com.districtcore.coresentinel.annotation.ValidEntityIds;
import com.districtcore.coresentinel.util.EntityPrefixUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityIdValidator implements ConstraintValidator<ValidEntityIds, List<String>> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(ValidEntityIds constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
    }


    @Override
    public boolean isValid(List<String> prefixedIds, ConstraintValidatorContext context) {
        if (prefixedIds == null || prefixedIds.isEmpty()) {
            return true;
        }

        if (entityClass == null) {
            throw new IllegalStateException("Entity class is not set");
        }

        boolean arePrefixesCorrect = prefixedIds.stream()
                .allMatch(prefixedId -> prefixedId.charAt(0) == EntityPrefixUtils.getClassPrefix(entityClass));

        if (!arePrefixesCorrect) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid ID prefixes")
                    .addConstraintViolation();
            return false;
        }

        boolean allNumeric = prefixedIds.stream()
                .allMatch(id -> id.length() > 1 && id.substring(1).matches("\\d+"));

        if (!allNumeric) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("One or more IDs have an invalid numeric part")
                    .addConstraintViolation();
            return false;
        }

        Set<Long> ids = prefixedIds.stream()
                .map(prefixedId -> Long.parseLong(prefixedId.substring(1)))
                .collect(Collectors.toSet());

        Set<Long> existingIds = entityManager.createQuery(
                        "SELECT e.id FROM " + entityClass.getSimpleName() + " e WHERE e.id IN :ids", Long.class)
                .setParameter("ids", ids)
                .getResultStream()
                .collect(Collectors.toSet());

        List<Long> invalidIds = ids.stream()
                .filter(id -> !existingIds.contains(id))
                .toList();

        if (!invalidIds.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid entity IDs: " + invalidIds)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
