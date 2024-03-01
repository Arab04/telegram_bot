package fabrico.nova.optics.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_number")
    private String customerNumber;

    @Column(name = "notification_data")
    private String notificationData;

    @Column(name = "linza_type")
    private String linza;
}
