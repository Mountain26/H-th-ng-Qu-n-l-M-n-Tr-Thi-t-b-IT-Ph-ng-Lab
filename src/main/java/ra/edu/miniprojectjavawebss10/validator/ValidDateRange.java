package ra.edu.miniprojectjavawebss10.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default "Ngày trả phải sau ngày mượn";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
