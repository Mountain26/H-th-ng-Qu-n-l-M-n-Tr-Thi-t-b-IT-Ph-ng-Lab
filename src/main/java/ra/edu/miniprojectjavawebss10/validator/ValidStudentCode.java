package ra.edu.miniprojectjavawebss10.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = StudentCodeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStudentCode {
    String message() default "Mã sinh viên phải có ít nhất 3 ký tự, 2 ký tự đầu là chữ và các ký tự sau là số";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
