package fabrico.nova.optics.service.customer;

import fabrico.nova.optics.model.CustomerEntity;
import fabrico.nova.optics.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerEntity saveCustomer(CustomerEntity customer) {
        LocalDate currentDate = LocalDate.now();
        LocalDate newDate = currentDate.plusMonths(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        CustomerEntity entity = new CustomerEntity();
        entity.setCustomerNumber(customer.getCustomerNumber());
        entity.setLinza(customer.getLinza());
        entity.setCustomerName("none");
        entity.setDeleted(false);
        entity.setRegisteredDate(currentDate.format(formatter));
        entity.setNotificationData(newDate.format(formatter));
        customerRepository.save(entity);
        return entity;
    }
}
