package ra.edu.miniprojectjavawebss10.repository;

import org.springframework.stereotype.Repository;
import ra.edu.miniprojectjavawebss10.model.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private final List<User> users = new ArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    public UserRepository() {
        users.add(new User(idGenerator.getAndIncrement(), "admin", "admin123", "ADMIN", "admin@gmail.com", "0123456789"));
        users.add(new User(idGenerator.getAndIncrement(), "student", "student123", "STUDENT", "student@gmail.com", "0987654321"));
    }

    public void save(User user) {
        user.setId(idGenerator.getAndIncrement());
        users.add(user);
    }

    public User findByUsername(String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public boolean existsByUsername(String username) {
        return users.stream().anyMatch(u -> u.getUsername().equals(username));
    }
}
