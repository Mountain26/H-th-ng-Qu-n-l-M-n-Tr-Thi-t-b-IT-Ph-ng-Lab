package ra.edu.miniprojectjavawebss10.service;

import ra.edu.miniprojectjavawebss10.model.entity.BorrowRequest;

import java.util.List;

public interface BorrowService {
    void save(BorrowRequest request);

    List<BorrowRequest> findAll();
}
