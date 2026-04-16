package ra.edu.miniprojectjavawebss10.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DeviceFormDTO {
    @NotBlank(message = "Ten thiet bi khong duoc de trong")
    private String name;

    @NotBlank(message = "Anh minh hoa khong duoc de trong")
    private String image;

    @NotNull(message = "So luong khong duoc de trong")
    @Min(value = 0, message = "So luong phai lon hon hoac bang 0")
    private Integer quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

