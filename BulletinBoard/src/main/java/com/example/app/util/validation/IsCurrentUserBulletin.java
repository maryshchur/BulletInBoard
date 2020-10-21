package com.example.app.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = IsCurrentUserBulletinValidator.class)
@Documented
public @interface IsCurrentUserBulletin {
    String message() default "You can not like your own bulletin ";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
