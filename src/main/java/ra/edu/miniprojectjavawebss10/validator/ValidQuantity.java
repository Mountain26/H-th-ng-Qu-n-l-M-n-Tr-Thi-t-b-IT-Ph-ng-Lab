package ra.edu.miniprojectjavawebss10.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = QuantityValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuantity {
    String message() default "Số lượng mượn không được vượt quá số lượng còn lại";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}