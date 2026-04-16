package ra.edu.miniprojectjavawebss10.validator;

import ra.edu.miniprojectjavawebss10.model.dto.BorrowRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, BorrowRequestDTO> {

    @Override
    public boolean isValid(BorrowRequestDTO request, ConstraintValidatorContext context) {
        if (request == null) return true;

        LocalDate borrowDate = request.getBorrowDate();
        LocalDate returnDate = request.getReturnDate();

        if (borrowDate == null || returnDate == null) {
            return true;
        }

        if (!returnDate.isAfter(borrowDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                   .addPropertyNode("returnDate").addConstraintViolation();
            return false;
        }

        return true;
    }
}
