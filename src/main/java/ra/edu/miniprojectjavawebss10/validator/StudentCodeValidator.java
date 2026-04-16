package ra.edu.miniprojectjavawebss10.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StudentCodeValidator implements ConstraintValidator<ValidStudentCode, String> {
    @Override
    public boolean isValid(String studentCode, ConstraintValidatorContext context) {
        if (studentCode == null) {
            return true;
        }

        String value = studentCode.trim();
        if (value.length() < 3) {
            return false;
        }

        if (!Character.isLetter(value.charAt(0)) || !Character.isLetter(value.charAt(1))) {
            return false;
        }

        for (int i = 2; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}