package ra.edu.miniprojectjavawebss10.service;

import ra.edu.miniprojectjavawebss10.model.entity.Device;

import java.util.List;

public interface DeviceService {
    List<Device> findAll();

    Device findById(int id);

    Device create(Device device);

    boolean update(Device device);

    boolean deleteById(int id);
}
