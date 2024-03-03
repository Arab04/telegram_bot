package fabrico.nova.optics.repository;

import fabrico.nova.optics.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "SELECT * FROM customers WHERE notification_data = :notification_date", nativeQuery = true)
    List<CustomerEntity> listOfCustomers(@Param("notification_date") String notificationDate);
}
