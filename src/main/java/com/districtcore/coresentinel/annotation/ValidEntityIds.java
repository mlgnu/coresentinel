package com.districtcore.coresentinel.annotation;

import com.districtcore.coresentinel.validator.ListEntityIdValidator;
import com.districtcore.coresentinel.validator.SingleEntityIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { SingleEntityIdValidator.class, ListEntityIdValidator.class })
@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidEntityIds {
    String message() default "Invalid entity IDs";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    Class<?> entityClass();
}
