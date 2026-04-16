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
import ra.edu.miniprojectjavawebss10.model.dto.DeviceFormDTO;
import ra.edu.miniprojectjavawebss10.model.entity.BorrowRequest;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.service.BorrowService;
import ra.edu.miniprojectjavawebss10.service.DeviceService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final DeviceService deviceService;
    private final BorrowService borrowService;

    public AdminController(DeviceService deviceService, BorrowService borrowService) {
        this.deviceService = deviceService;
        this.borrowService = borrowService;
    }

    @GetMapping({"", "/dashboard"})
    public String dashboard(Model model) {
        List<Device> devices = deviceService.findAll();
        List<BorrowRequest> requests = borrowService.findAll();

        int totalStock = devices.stream().mapToInt(Device::getQuantity).sum();
        int totalPending = (int) requests.stream().filter(r -> BorrowRequest.STATUS_PENDING.equals(r.getStatus())).count();
        int totalApproved = (int) requests.stream().filter(r -> BorrowRequest.STATUS_APPROVED.equals(r.getStatus())).count();

        model.addAttribute("totalDevices", devices.size());
        model.addAttribute("totalStock", totalStock);
        model.addAttribute("totalRequests", requests.size());
        model.addAttribute("totalPending", totalPending);
        model.addAttribute("totalApproved", totalApproved);
        model.addAttribute("recentRequests", requests.stream().limit(5).toList());
        return "admin/dashboard";
    }

    @GetMapping("/devices")
    public String listDevices(Model model) {
        model.addAttribute("devices", deviceService.findAll());
        return "admin/device-list";
    }

    @GetMapping("/devices/create")
    public String showCreateForm(Model model) {
        model.addAttribute("deviceForm", new DeviceFormDTO());
        model.addAttribute("pageTitle", "Thêm thiết bị");
        model.addAttribute("submitUrl", "/admin/devices/create");
        return "admin/device-form";
    }

    @PostMapping("/devices/create")
    public String createDevice(
            @Valid @ModelAttribute("deviceForm") DeviceFormDTO form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Thêm thiết bị");
            model.addAttribute("submitUrl", "/admin/devices/create");
            return "admin/device-form";
        }

        deviceService.create(toEntity(form, 0));
        redirectAttributes.addFlashAttribute("successMessage", "Thêm thiết bị thành công.");
        return "redirect:/admin/devices";
    }

    @GetMapping("/devices/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
        Device device = deviceService.findById(id);
        if (device == null) {
            redirectAttributes.addFlashAttribute("successMessage", "Không tìm thấy thiết bị.");
            return "redirect:/admin/devices";
        }

        DeviceFormDTO form = new DeviceFormDTO();
        form.setName(device.getName());
        form.setImage(device.getImage());
        form.setQuantity(device.getQuantity());

        model.addAttribute("deviceId", id);
        model.addAttribute("deviceForm", form);
        model.addAttribute("pageTitle", "Cập nhật thiết bị");
        model.addAttribute("submitUrl", "/admin/devices/edit/" + id);
        return "admin/device-form";
    }

    @PostMapping("/devices/edit/{id}")
    public String updateDevice(
            @PathVariable("id") int id,
            @Valid @ModelAttribute("deviceForm") DeviceFormDTO form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("deviceId", id);
            model.addAttribute("pageTitle", "Cập nhật thiết bị");
            model.addAttribute("submitUrl", "/admin/devices/edit/" + id);
            return "admin/device-form";
        }

        boolean updated = deviceService.update(toEntity(form, id));
        if (!updated) {
            redirectAttributes.addFlashAttribute("successMessage", "Không tìm thấy thiết bị để cập nhật.");
            return "redirect:/admin/devices";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thiết bị thành công.");
        return "redirect:/admin/devices";
    }

    @PostMapping("/devices/delete/{id}")
    public String deleteDevice(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        boolean deleted = deviceService.deleteById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Xóa thiết bị thành công.");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Không tìm thấy thiết bị để xóa.");
        }
        return "redirect:/admin/devices";
    }

    @GetMapping("/requests")
    public String listRequests(Model model) {
        model.addAttribute("borrowRequests", borrowService.findAll());
        return "admin/request-list";
    }

    @PostMapping("/requests/{id}/approve")
    public String approveRequest(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        boolean approved = borrowService.approve(id);
        if (approved) {
            redirectAttributes.addFlashAttribute("successMessage", "Đã duyệt yêu cầu mượn.");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Không thể duyệt: yêu cầu không hợp lệ hoặc thiết bị không đủ số lượng.");
        }
        return "redirect:/admin/requests";
    }

    @PostMapping("/requests/{id}/reject")
    public String rejectRequest(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        boolean rejected = borrowService.reject(id);
        if (rejected) {
            redirectAttributes.addFlashAttribute("successMessage", "Đã từ chối yêu cầu mượn.");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Không thể từ chối yêu cầu này.");
        }
        return "redirect:/admin/requests";
    }

    private Device toEntity(DeviceFormDTO form, int id) {
        Device device = new Device();
        device.setId(id);
        device.setName(form.getName());
        device.setImage(form.getImage());
        device.setQuantity(form.getQuantity());
        return device;
    }
}
