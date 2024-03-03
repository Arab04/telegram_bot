package fabrico.nova.optics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private String customerName;
    private String customerNumber;
    private String notificationData;
    private String linza;
}
