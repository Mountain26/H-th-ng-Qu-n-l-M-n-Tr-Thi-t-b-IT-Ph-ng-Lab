package ra.edu.miniprojectjavawebss10.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ra.edu.miniprojectjavawebss10.model.dto.BorrowRequestDTO;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.service.DeviceService;

@Component
public class QuantityValidator implements ConstraintValidator<ValidQuantity, BorrowRequestDTO> {
    private final DeviceService deviceService;

    public QuantityValidator(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public boolean isValid(BorrowRequestDTO request, ConstraintValidatorContext context) {
        if (request == null || request.getQuantity() == null) {
            return true;
        }

        Device device = deviceService.findById(request.getDeviceId());
        if (device == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Thiết bị không tồn tại")
                    .addPropertyNode("deviceId")
                    .addConstraintViolation();
            return false;
        }

        if (request.getQuantity() > device.getQuantity()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("quantity")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}