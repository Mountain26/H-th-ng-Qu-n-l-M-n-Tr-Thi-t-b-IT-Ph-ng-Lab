package ra.edu.miniprojectjavawebss10.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import ra.edu.miniprojectjavawebss10.validator.ValidDateRange;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@ValidDateRange
public class BorrowRequestDTO {

    private int deviceId;

    @NotBlank(message = "Họ tên không được để trống")
    private String studentName;

    @NotBlank(message = "Mã sinh viên không được để trống")
    @Pattern(regexp = "^[A-Za-z]{2}\\d+$", message = "Mã sinh viên phải bắt đầu bằng 2 chữ cái, theo sau là số")
    private String studentCode;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải là số nguyên dương")
    private Integer quantity;

    @NotNull(message = "Ngày mượn không được để trống")
    @Future(message = "Ngày mượn phải ở tương lai")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate borrowDate;

    @NotNull(message = "Ngày trả không được để trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @NotBlank(message = "Lý do mượn không được để trống")
    private String reason;

    // Getters and Setters
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

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
