package ra.edu.miniprojectjavawebss10.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ra.edu.miniprojectjavawebss10.model.dto.BorrowRequestDTO;
import ra.edu.miniprojectjavawebss10.model.entity.BorrowRequest;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.service.BorrowService;
import ra.edu.miniprojectjavawebss10.service.DeviceService;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private BorrowService borrowService;

    // REQ-S01: Lấy danh sách thiết bị
    @GetMapping({"", "/devices"})
    public String listDevices(Model model) {
        List<Device> devices = deviceService.findAll();
        model.addAttribute("devices", devices);
        return "student/device-list"; // View name
    }

    // Hiển thị form mượn thiết bị
    @GetMapping("/borrow/{deviceId}")
    public String showBorrowForm(@PathVariable("deviceId") int deviceId, Model model) {
        Device device = deviceService.findById(deviceId);
        if (device == null) {
            return "redirect:/student/devices";
        }
        BorrowRequestDTO dto = new BorrowRequestDTO();
        dto.setDeviceId(deviceId);

        model.addAttribute("device", device);
        model.addAttribute("borrowRequest", dto);
        return "student/borrow-form";
    }

    // REQ-S02: Xử lý submit form
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

        borrowService.save(request);
        redirectAttributes.addFlashAttribute("successMessage", "Dang ky thanh cong. Yeu cau muon da duoc gui.");

        return "redirect:/student/devices";
    }
}
