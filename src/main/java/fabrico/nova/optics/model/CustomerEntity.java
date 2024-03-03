package fabrico.nova.optics.model;

import fabrico.nova.optics.model.constants.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customers")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "notification_data")
    private String notificationData;

    @Column(name = "registered_date")
    private String registeredDate;

    @Column(name = "linza_type")
    private String linza;

    @Column(name = "deleted_customer")
    private Boolean deleted;

    @Column(name = "seeing_doctor")
    private Boolean seeingDoctor = false;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;

    @ManyToOne(cascade = CascadeType.ALL)
    private UserEntity user;

    public CustomerEntity() {}
    public CustomerEntity(String customerNumber) {
        this.customerNumber = customerNumber;
    }
}
