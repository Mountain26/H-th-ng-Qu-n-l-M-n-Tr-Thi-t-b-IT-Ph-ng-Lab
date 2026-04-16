package ra.edu.miniprojectjavawebss10.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ra.edu.miniprojectjavawebss10.model.dto.BorrowRequestDTO;
// Class xử lý logic cho annotation @ValidQuantity
// Dùng để kiểm tra số lượng mượn có vượt quá số lượng còn lại không
public class QuantityValidator implements ConstraintValidator<ValidQuantity, BorrowRequestDTO> {
	@Override
	public boolean isValid(BorrowRequestDTO request, ConstraintValidatorContext context) {
		// Nếu object null thì không validate tránh lỗi
		if (request == null) {
			return true;
		}
		// Lấy dữ liệu từ DTO
		Integer quantity = request.getQuantity(); // số lượng sinh viên nhập
		Integer availableQuantity = request.getAvailableQuantity(); // số lượng còn lại trong kho

		// Nếu chưa có dữ liệu thì để annotation khác xử lý (@NotNull, @Min)
		if (quantity == null || availableQuantity == null) {
			return true;
		}
		// Kiểm tra: số lượng mượn không được vượt quá số lượng còn lại
		if (quantity > availableQuantity) {
			// Tắt message mặc định
			context.disableDefaultConstraintViolation();
			// Gán lỗi vào field "quantity" để hiển thị đúng chỗ trên form
			context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
					.addPropertyNode("quantity")
					.addConstraintViolation();
			return false;
		}
		return true;
	}
}