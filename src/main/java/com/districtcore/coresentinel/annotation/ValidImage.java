package com.districtcore.coresentinel.annotation;

import com.districtcore.coresentinel.validator.ImageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
public @interface ValidImage {
    String message() default "Uploaded file is not a valid image";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

