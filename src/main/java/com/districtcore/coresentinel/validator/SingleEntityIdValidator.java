package com.districtcore.coresentinel.validator;

import com.districtcore.coresentinel.annotation.ValidEntityIds;
import com.districtcore.coresentinel.util.EntityPrefixUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SingleEntityIdValidator implements ConstraintValidator<ValidEntityIds, String> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<?> entityClass;

    @Override
    public void initialize(ValidEntityIds constraintAnnotation) {
        this.entityClass = constraintAnnotation.entityClass();
    }

    @Override
    public boolean isValid(String prefixedId, ConstraintValidatorContext context) {
        if (prefixedId == null || prefixedId.isEmpty()) {
            return true;
        }

        char expectedPrefix = EntityPrefixUtils.getClassPrefix(entityClass);
        if (prefixedId.charAt(0) != expectedPrefix) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid ID prefix: expected " + expectedPrefix)
                    .addConstraintViolation();
            return false;
        }

        if (prefixedId.length() <= 1 || !prefixedId.substring(1).matches("\\d+")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid numeric part in ID")
                    .addConstraintViolation();
            return false;
        }

        Long id;
        try {
            id = Long.parseLong(prefixedId.substring(1));
        } catch (NumberFormatException e) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("ID numeric conversion error")
                    .addConstraintViolation();
            return false;
        }

        Long count = entityManager.createQuery(
                        "SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e WHERE e.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
        if (count == null || count == 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Entity with ID " + prefixedId + " does not exist")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
