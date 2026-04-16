package ra.edu.miniprojectjavawebss10.repository;

import org.springframework.stereotype.Repository;
import ra.edu.miniprojectjavawebss10.model.entity.BorrowRequest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BorrowRepository {
    private final List<BorrowRequest> requests = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public void save(BorrowRequest request) {
        request.setId(idGenerator.getAndIncrement());
        requests.add(request);
    }

    public List<BorrowRequest> findAll() {
        return requests.stream()
                .sorted(Comparator.comparingInt(BorrowRequest::getId).reversed())
                .toList();
    }

    public BorrowRequest findById(int id) {
        return requests.stream()
                .filter(request -> request.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
