package ra.edu.miniprojectjavawebss10.model.entity;

public class Device {
    private int id;
    private String name;
    private String image;
    private int quantity;

    public Device() {}

    public Device(int id, String name, String image, int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}