package flaviodeangeelis.u5w2d5.repositories;

import flaviodeangeelis.u5w2d5.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
    Optional<Device> findByUser_id(int id);
}
