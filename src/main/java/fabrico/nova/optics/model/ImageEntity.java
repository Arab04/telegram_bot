package fabrico.nova.optics.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter
@Setter
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "image_data", length = 1000)
    private byte[] imageData;

    @ManyToOne(cascade = CascadeType.ALL)
    private CustomerEntity customerEntity;
}
