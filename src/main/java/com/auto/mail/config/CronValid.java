package com.auto.mail.config;


import org.springframework.scheduling.support.CronSequenceGenerator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CronValid.CronValidator.class})
@Documented
public @interface CronValid {

    String message() default "{javax.validation.constraints.NotNull.message}";

    class CronValidator implements ConstraintValidator<CronValid, String> {


        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {

            if (value == null) {
                return false;
            }
            try {
                new CronSequenceGenerator(value);
                return  true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
