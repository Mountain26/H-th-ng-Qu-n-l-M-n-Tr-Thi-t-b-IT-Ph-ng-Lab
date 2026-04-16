package ra.edu.miniprojectjavawebss10.service;

import org.springframework.stereotype.Service;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.repository.DeviceRepository;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public Device findById(int id) {
        return deviceRepository.findById(id);
    }

    public Device create(Device device) {
        return deviceRepository.save(device);
    }

    public boolean update(Device device) {
        return deviceRepository.update(device);
    }

    public boolean deleteById(int id) {
        return deviceRepository.deleteById(id);
    }
}
