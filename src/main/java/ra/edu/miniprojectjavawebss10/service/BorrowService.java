package ra.edu.miniprojectjavawebss10.service;

import org.springframework.stereotype.Service;
import ra.edu.miniprojectjavawebss10.model.entity.BorrowRequest;
import ra.edu.miniprojectjavawebss10.model.entity.Device;
import ra.edu.miniprojectjavawebss10.repository.BorrowRepository;
import ra.edu.miniprojectjavawebss10.repository.DeviceRepository;

import java.util.List;

@Service
public class BorrowService {
    private final BorrowRepository borrowRepository;
    private final DeviceRepository deviceRepository;

    public BorrowService(BorrowRepository borrowRepository, DeviceRepository deviceRepository) {
        this.borrowRepository = borrowRepository;
        this.deviceRepository = deviceRepository;
    }

    public void save(BorrowRequest request) {
        if (request == null || request.getDevice() == null) {
            throw new IllegalArgumentException("Yêu cầu không hợp lệ");
        }

        Device currentDevice = deviceRepository.findById(request.getDevice().getId());
        if (currentDevice == null) {
            throw new IllegalArgumentException("Thiết bị không tồn tại");
        }

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Số lượng mượn phải lớn hơn 0");
        }

        request.setDevice(currentDevice);
        request.setStatus(BorrowRequest.STATUS_PENDING);
        borrowRepository.save(request);
    }

    public List<BorrowRequest> findAll() {
        return borrowRepository.findAll();
    }

    public boolean approve(int requestId) {
        BorrowRequest request = borrowRepository.findById(requestId);
        if (request == null || !BorrowRequest.STATUS_PENDING.equals(request.getStatus())) {
            return false;
        }

        Device currentDevice = deviceRepository.findById(request.getDevice().getId());
        if (currentDevice == null || currentDevice.getQuantity() < request.getQuantity()) {
            return false;
        }

        currentDevice.setQuantity(currentDevice.getQuantity() - request.getQuantity());
        deviceRepository.update(currentDevice);
        request.setDevice(currentDevice);
        request.setStatus(BorrowRequest.STATUS_APPROVED);
        return true;
    }

    public boolean reject(int requestId) {
        BorrowRequest request = borrowRepository.findById(requestId);
        if (request == null || !BorrowRequest.STATUS_PENDING.equals(request.getStatus())) {
            return false;
        }

        request.setStatus(BorrowRequest.STATUS_REJECTED);
        return true;
    }
}
