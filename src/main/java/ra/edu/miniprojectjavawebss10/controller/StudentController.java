package ra.edu.miniprojectjavawebss10.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.edu.miniprojectjavawebss10.model.dto.BorrowRequestDTO;
import ra.edu.miniprojectjavawebss10.model.entity.BorrowRequest;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.service.BorrowService;
import ra.edu.miniprojectjavawebss10.service.DeviceService;

import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final DeviceService deviceService;
    private final BorrowService borrowService;

    public StudentController(DeviceService deviceService, BorrowService borrowService) {
        this.deviceService = deviceService;
        this.borrowService = borrowService;
    }

    @GetMapping({"", "/devices"})
    public String listDevices(Model model) {
        List<Device> devices = deviceService.findAll();
        model.addAttribute("devices", devices);
        return "student/device-list";
    }

    @GetMapping("/borrow/{deviceId}")
    public String showBorrowForm(@PathVariable("deviceId") int deviceId, Model model) {
        Device device = deviceService.findById(deviceId);
        if (device == null || device.getQuantity() <= 0) {
            return "redirect:/student/devices";
        }

        BorrowRequestDTO dto = new BorrowRequestDTO();
        dto.setDeviceId(deviceId);

        model.addAttribute("device", device);
        model.addAttribute("borrowRequest", dto);
        return "student/borrow-form";
    }

    @PostMapping("/borrow")
    public String submitBorrowForm(
            @Valid @ModelAttribute("borrowRequest") BorrowRequestDTO borrowRequestDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Device device = deviceService.findById(borrowRequestDTO.getDeviceId());
        if (device == null) {
            return "redirect:/student/devices";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("device", device);
            return "student/borrow-form";
        }

        BorrowRequest request = new BorrowRequest();
        request.setStudentName(borrowRequestDTO.getStudentName());
        request.setStudentCode(borrowRequestDTO.getStudentCode());
        request.setEmail(borrowRequestDTO.getEmail());
        request.setQuantity(borrowRequestDTO.getQuantity());
        request.setBorrowDate(borrowRequestDTO.getBorrowDate());
        request.setReturnDate(borrowRequestDTO.getReturnDate());
        request.setReason(borrowRequestDTO.getReason());
        request.setDevice(device);

        try {
            borrowService.save(request);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("quantity", "borrow.quantity.exceeded", ex.getMessage());
            model.addAttribute("device", device);
            return "student/borrow-form";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công. Yêu cầu mượn đã được gửi.");
        return "redirect:/student/devices";
    }
}
