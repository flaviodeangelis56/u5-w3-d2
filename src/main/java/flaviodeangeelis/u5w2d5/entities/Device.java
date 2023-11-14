package flaviodeangeelis.u5w2d5.entities;


import flaviodeangeelis.u5w2d5.enumType.DeviceStatus;
import flaviodeangeelis.u5w2d5.enumType.DeviceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;
    @Enumerated(EnumType.STRING)
    private DeviceStatus deviceStatus;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
