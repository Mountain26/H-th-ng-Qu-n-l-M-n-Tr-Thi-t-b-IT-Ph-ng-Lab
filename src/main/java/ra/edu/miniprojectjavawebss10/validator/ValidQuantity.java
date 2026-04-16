package ra.edu.miniprojectjavawebss10.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
// Đánh dấu đây là annotation custom dùng cho validation
@Documented
// Liên kết annotation này với class xử lý logic là QuantityValidator
@Constraint(validatedBy = QuantityValidator.class)
// Áp dụng cho cả class (TYPE) vì cần so sánh nhiều field (quantity và availableQuantity)
@Target({ElementType.TYPE})
// Annotation này được giữ lại trong runtime để Spring sử dụng khi validate
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuantity {
	// Thông báo lỗi mặc định khi validate fail
	// Trường hợp: số lượng mượn > số lượng còn lại trong kho
	String message() default "Số lượng mượn không được vượt quá số lượng còn lại";
	// Dùng cho nhóm validation
	Class<?>[] groups() default {};
	// Dùng để truyền metadata bổ sung
	Class<? extends Payload>[] payload() default {};
}