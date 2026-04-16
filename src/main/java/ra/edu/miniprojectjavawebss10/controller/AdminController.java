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

    @GetMapping({"", "/dashboard", "/devices"})
    public String listDevices(Model model) {
        model.addAttribute("devices", deviceService.findAll());
        return "admin/dashboard";
    }

    @GetMapping("/devices/create")
    public String showCreateForm(Model model) {
        model.addAttribute("deviceForm", new DeviceFormDTO());
        model.addAttribute("pageTitle", "Them thiet bi");
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
            model.addAttribute("pageTitle", "Them thiet bi");
            model.addAttribute("submitUrl", "/admin/devices/create");
            return "admin/device-form";
        }

        deviceService.create(toEntity(form, 0));
        redirectAttributes.addFlashAttribute("successMessage", "Them thiet bi thanh cong.");
        return "redirect:/admin/devices";
    }

    @GetMapping("/devices/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model, RedirectAttributes redirectAttributes) {
        Device device = deviceService.findById(id);
        if (device == null) {
            redirectAttributes.addFlashAttribute("successMessage", "Khong tim thay thiet bi.");
            return "redirect:/admin/devices";
        }

        DeviceFormDTO form = new DeviceFormDTO();
        form.setName(device.getName());
        form.setImage(device.getImage());
        form.setQuantity(device.getQuantity());

        model.addAttribute("deviceId", id);
        model.addAttribute("deviceForm", form);
        model.addAttribute("pageTitle", "Cap nhat thiet bi");
        model.addAttribute("submitUrl", "/admin/devices/edit/" + id);
        return "admin/device-form";
    }

    @PostMapping("/devices/edit/{id}")
    public String updateDevice(
            @PathVariable int id,
            @Valid @ModelAttribute("deviceForm") DeviceFormDTO form,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("deviceId", id);
            model.addAttribute("pageTitle", "Cap nhat thiet bi");
            model.addAttribute("submitUrl", "/admin/devices/edit/" + id);
            return "admin/device-form";
        }

        boolean updated = deviceService.update(toEntity(form, id));
        if (!updated) {
            redirectAttributes.addFlashAttribute("successMessage", "Khong tim thay thiet bi de cap nhat.");
            return "redirect:/admin/devices";
        }

        redirectAttributes.addFlashAttribute("successMessage", "Cap nhat thiet bi thanh cong.");
        return "redirect:/admin/devices";
    }

    @PostMapping("/devices/delete/{id}")
    public String deleteDevice(@PathVariable int id, RedirectAttributes redirectAttributes) {
        boolean deleted = deviceService.deleteById(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Xoa thiet bi thanh cong.");
        } else {
            redirectAttributes.addFlashAttribute("successMessage", "Khong tim thay thiet bi de xoa.");
        }
        return "redirect:/admin/devices";
    }

    @GetMapping("/requests")
    public String listRequests(Model model) {
        List<BorrowRequest> borrowRequests = borrowService.findAll();
        model.addAttribute("borrowRequests", borrowRequests);
        return "admin/request-list";
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
