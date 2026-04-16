package ra.edu.miniprojectjavawebss10.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import ra.edu.miniprojectjavawebss10.validator.ValidDateRange;
import ra.edu.miniprojectjavawebss10.validator.ValidQuantity;
import ra.edu.miniprojectjavawebss10.validator.ValidStudentCode;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

// Annotation custom kiểm tra logic ngày (returnDate > borrowDate)
@ValidDateRange

// Annotation custom kiểm tra số lượng (quantity <= availableQuantity)
@ValidQuantity
public class BorrowRequestDTO {

    // id thiết bị được chọn để mượn
    private int deviceId;

    // Không được để trống tên sinh viên
    @NotBlank(message = "Họ tên không được để trống")
    private String studentName;

    // Không được để trống mã sinh viên
    @NotBlank(message = "Mã sinh viên không được để trống")
    // Gọi custom validator để kiểm tra format (2 chữ + số)
    @ValidStudentCode
    private String studentCode;

    // Không được để trống email
    @NotBlank(message = "Email không được để trống")
    // Kiểm tra đúng định dạng email
    @Email(message = "Email không đúng định dạng")
    private String email;

    // Không được null (bắt buộc nhập)
    @NotNull(message = "Số lượng không được để trống")
    // Phải >= 1 (số nguyên dương)
    @Min(value = 1, message = "Số lượng phải là số nguyên dương")
    private Integer quantity;

    // Số lượng còn lại trong kho
    // Dùng để so sánh với quantity trong validator
    private Integer availableQuantity;

    // Không được để trống ngày mượn
    @NotNull(message = "Ngày mượn không được để trống")
    // Phải là ngày trong tương lai (sau hôm nay)
    @Future(message = "Ngày mượn phải ở tương lai")
    // Format dữ liệu từ form HTML (yyyy-MM-dd)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;

    // Không được để trống ngày trả
    @NotNull(message = "Ngày trả không được để trống")
    // Format dữ liệu từ form HTML
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    // Không được để trống lý do mượn
    @NotBlank(message = "Lý do mượn không được để trống")
    private String reason;

    // ================= GETTER / SETTER =================
    // Dùng để Spring Data Binding map dữ liệu từ form HTML vào object DTO

    public int getDeviceId() { return deviceId; }
    public void setDeviceId(int deviceId) { this.deviceId = deviceId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentCode() { return studentCode; }
    public void setStudentCode(String studentCode) { this.studentCode = studentCode; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    // Lấy số lượng thiết bị còn lại trong kho
    // Dùng trong validator để so sánh với quantity (số lượng sinh viên muốn mượn)
    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    // Gán số lượng thiết bị còn lại (thường được set từ Controller hoặc Service)
    // Giá trị này KHÔNG nhập từ form mà lấy từ dữ liệu hệ thống
    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}