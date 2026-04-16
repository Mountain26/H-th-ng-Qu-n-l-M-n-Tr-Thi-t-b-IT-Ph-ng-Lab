package ra.edu.miniprojectjavawebss10.validator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
// Validator kiểm tra format mã sinh viên, 2 chữ + số
public class StudentCodeValidator implements ConstraintValidator<ValidStudentCode, String> {
    @Override
    public boolean isValid(String studentCode, ConstraintValidatorContext context) {
        // Nếu null thì để @NotBlank xử lý
        if (studentCode == null) {
            return true;
        }
        String value = studentCode.trim();
        // Độ dài tối thiểu phải >= 3, 2 chữ + ít nhất 1 số
        if (value.length() < 3) {
            return false;
        }
        // 2 ký tự đầu phải là chữ cái
        if (!Character.isLetter(value.charAt(0)) || !Character.isLetter(value.charAt(1))) {
            return false;
        }
        // Các ký tự còn lại phải là số
        for (int i = 2; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}