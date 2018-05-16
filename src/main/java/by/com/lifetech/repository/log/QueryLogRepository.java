package by.com.lifetech.repository.log;

import by.com.lifetech.model.QueryLog;
import by.com.lifetech.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QueryLogRepository extends JpaRepository<QueryLog, Long> {

    List<QueryLog> findByUser(User user);
}
