package com.districtcore.coresentinel.annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrefixedId {
    String value() default "id";
    String prefix() default "";
}
