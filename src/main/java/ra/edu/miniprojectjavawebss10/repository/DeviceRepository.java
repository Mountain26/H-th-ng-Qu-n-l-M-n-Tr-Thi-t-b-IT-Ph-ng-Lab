package ra.edu.miniprojectjavawebss10.repository;

import org.springframework.stereotype.Repository;
import ra.edu.miniprojectjavawebss10.model.entity.Device;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class DeviceRepository {
    private final List<Device> devices = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public DeviceRepository() {
        save(new Device(0, "Laptop Dell G15", "https://bizweb.dktcdn.net/100/408/235/files/dell-g5-5510-1.jpg?v=1635317802220", 10));
        save(new Device(0, "Máy chiếu Sony", "https://maychieubacha.vn/temp/uploaded-may%20chieu-Sony-VPL%20EX230_SONY%20VPL-EX230%20(2)_rs1_600x412.jpeg", 5));
        save(new Device(0, "Bảng vẽ Wacom", "https://tugia.vn/sites/default/files/bang-ve-may-tinh-wacom-cintiq-13hd-dtk-1301-k0-cx.jpg", 15));
    }

    public List<Device> findAll() {
        return devices.stream()
                .sorted(Comparator.comparingInt(Device::getId))
                .toList();
    }

    public Device findById(int id) {
        return devices.stream()
                .filter(device -> device.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Device save(Device device) {
        device.setId(idGenerator.getAndIncrement());
        devices.add(device);
        return device;
    }

    public boolean update(Device updatedDevice) {
        for (int i = 0; i < devices.size(); i++) {
            if (devices.get(i).getId() == updatedDevice.getId()) {
                devices.set(i, updatedDevice);
                return true;
            }
        }
        return false;
    }

    public boolean deleteById(int id) {
        return devices.removeIf(device -> device.getId() == id);
    }
}
