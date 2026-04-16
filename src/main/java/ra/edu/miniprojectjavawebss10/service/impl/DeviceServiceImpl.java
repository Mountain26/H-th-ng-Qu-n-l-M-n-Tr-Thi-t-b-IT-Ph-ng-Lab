package ra.edu.miniprojectjavawebss10.service.impl;

import org.springframework.stereotype.Service;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.repository.DeviceRepository;
import ra.edu.miniprojectjavawebss10.service.DeviceService;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public Device findById(int id) {
        return deviceRepository.findById(id);
    }

    @Override
    public Device create(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public boolean update(Device device) {
        return deviceRepository.update(device);
    }

    @Override
    public boolean deleteById(int id) {
        return deviceRepository.deleteById(id);
    }
}
