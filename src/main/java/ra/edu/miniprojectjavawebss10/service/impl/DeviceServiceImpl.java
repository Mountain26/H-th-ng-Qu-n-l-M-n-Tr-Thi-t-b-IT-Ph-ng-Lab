package ra.edu.miniprojectjavawebss10.service.impl;

import org.springframework.stereotype.Service;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.service.DeviceService;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final List<Device> mockDevices = new ArrayList<>();

    public DeviceServiceImpl() {
        mockDevices.add(new Device(1, "Laptop Dell G15", "https://via.placeholder.com/150?text=Dell+G15", 10));
        mockDevices.add(new Device(2, "Máy chiếu Sony", "https://via.placeholder.com/150?text=Projector", 5));
        mockDevices.add(new Device(3, "Bảng vẽ Wacom", "https://via.placeholder.com/150?text=Wacom", 15));
    }

    @Override
    public List<Device> findAll() {
        return mockDevices;
    }

    @Override
    public Device findById(int id) {
        return mockDevices.stream().filter(d -> d.getId() == id).findFirst().orElse(null);
    }
}
