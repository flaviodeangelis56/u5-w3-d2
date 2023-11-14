package flaviodeangeelis.u5w2d5.service;

import flaviodeangeelis.u5w2d5.entities.Device;
import flaviodeangeelis.u5w2d5.entities.User;
import flaviodeangeelis.u5w2d5.enumType.DeviceStatus;
import flaviodeangeelis.u5w2d5.enumType.DeviceType;
import flaviodeangeelis.u5w2d5.exception.BadRequestException;
import flaviodeangeelis.u5w2d5.exception.NotFoundException;
import flaviodeangeelis.u5w2d5.payload.NewDeviceDTO;
import flaviodeangeelis.u5w2d5.payload.UpdateDeviceStatusDTO;
import flaviodeangeelis.u5w2d5.payload.UpdateDeviceUserDTO;
import flaviodeangeelis.u5w2d5.repositories.DeviceRepository;
import flaviodeangeelis.u5w2d5.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private UserRepository userRepository;

    public Page<Device> getDevices(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return deviceRepository.findAll(pageable);
    }

    public Device findById(int id) {
        return deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Device save(NewDeviceDTO body) {
        Device newDevice = new Device();
        if (body.deviceType() == null) {
            throw new BadRequestException("Il tipo del dispositivo è un campo obbligatorio! Scegli tra SMARTPHONE,TABLET,LAPTOP");
        } else if (body.deviceType().trim().toUpperCase().contains("SMARTPHONE")) {
            newDevice.setDeviceStatus(DeviceStatus.DISPONIBILE);
            newDevice.setDeviceType(DeviceType.SMARTPHONE);
            newDevice.setUser(null);
            return deviceRepository.save(newDevice);
        } else if (body.deviceType().trim().toUpperCase().contains("TABLET")) {
            newDevice.setDeviceStatus(DeviceStatus.DISPONIBILE);
            newDevice.setDeviceType(DeviceType.TABLET);
            newDevice.setUser(null);
            return deviceRepository.save(newDevice);
        } else if (body.deviceType().trim().toUpperCase().contains("LAPTOP")) {
            newDevice.setDeviceStatus(DeviceStatus.DISPONIBILE);
            newDevice.setDeviceType(DeviceType.LAPTOP);
            newDevice.setUser(null);
            return deviceRepository.save(newDevice);
        } else {
            throw new BadRequestException("valore inserito non valido o non di tipo stringa! Scegli tra SMARTPHONE,TABLET,LAPTOP");
        }
    }

    public void findByIdAndDelete(int id) throws NotFoundException {
        Device found = this.findById(id);
        deviceRepository.delete(found);
    }

    public Device findByIdAndUpdateStatus(int id, UpdateDeviceStatusDTO body) {
        Device found = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        if (body.deviceStatus() == null) {
            throw new BadRequestException("Il tipo del dispositivo è un campo obbligatorio! Scegli tra DISPONIBILE,MANUTENZIONE,DISMESSO");
        } else if (body.deviceStatus().trim().toUpperCase().contains("DISPONIBILE")) {
            found.setUser(null);
            found.setDeviceType(found.getDeviceType());
            found.setDeviceStatus(DeviceStatus.DISPONIBILE);
            found.setId(found.getId());
            return deviceRepository.save(found);
        } else if (body.deviceStatus().trim().toUpperCase().contains("DISMESSO")) {
            found.setUser(null);
            found.setDeviceType(found.getDeviceType());
            found.setDeviceStatus(DeviceStatus.DISMESSO);
            found.setId(found.getId());
            return deviceRepository.save(found);
        } else if (body.deviceStatus().trim().toUpperCase().contains("MANUTENZIONE")) {
            found.setUser(null);
            found.setDeviceType(found.getDeviceType());
            found.setDeviceStatus(DeviceStatus.MANUTENZIONE);
            found.setId(found.getId());
            return deviceRepository.save(found);
        } else if (body.deviceStatus().trim().toUpperCase().contains("ASSEGNATO")) {
            throw new BadRequestException("per assegnare un dispositivo cambia il suo utente e verrà fatto in automatico");
        } else {
            throw new BadRequestException("valore inserito non valido o non di tipo stringa! Scegli tra DISPONIBILE,MANUTENZIONE,DISMESSO");
        }

    }

    public Device findByIdAndUpdateUser(int id, UpdateDeviceUserDTO body) {
        Device found = deviceRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
        Optional<Device> idFound = deviceRepository.findByUser_id(body.userId());
        if (idFound.isEmpty()) {
            User userFound = userRepository.findById(body.userId()).orElseThrow(() -> new NotFoundException(body.userId()));
            found.setUser(userFound);
            found.setDeviceType(found.getDeviceType());
            found.setDeviceStatus(DeviceStatus.ASSEGNATO);
            found.setId(found.getId());
            return deviceRepository.save(found);
        } else if (found.getDeviceStatus() == DeviceStatus.ASSEGNATO) {
            throw new BadRequestException("dispositivo già assegnato se vuoi cambiare utente rendilo prima disponibile");
        } else {
            throw new BadRequestException("l'utente ha già un dispositivo, per le politiche della azienda non può averne più di uno");
        }


    }
}
