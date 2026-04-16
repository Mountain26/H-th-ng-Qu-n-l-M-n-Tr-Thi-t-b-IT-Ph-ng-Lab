package ra.edu.miniprojectjavawebss10.service.impl;

import org.springframework.stereotype.Service;
import ra.edu.miniprojectjavawebss10.model.entity.BorrowRequest;
import ra.edu.miniprojectjavawebss10.repository.BorrowRepository;
import ra.edu.miniprojectjavawebss10.service.BorrowService;

import java.util.List;

@Service
public class BorrowServiceImpl implements BorrowService {
    private final BorrowRepository borrowRepository;

    public BorrowServiceImpl(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public void save(BorrowRequest request) {
        borrowRepository.save(request);
    }

    @Override
    public List<BorrowRequest> findAll() {
        return borrowRepository.findAll();
    }
}
