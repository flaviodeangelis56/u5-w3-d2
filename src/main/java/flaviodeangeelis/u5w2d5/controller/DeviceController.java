package flaviodeangeelis.u5w2d5.controller;

import flaviodeangeelis.u5w2d5.entities.Device;
import flaviodeangeelis.u5w2d5.exception.BadRequestException;
import flaviodeangeelis.u5w2d5.exception.NotFoundException;
import flaviodeangeelis.u5w2d5.payload.NewDeviceDTO;
import flaviodeangeelis.u5w2d5.payload.UpdateDeviceStatusDTO;
import flaviodeangeelis.u5w2d5.payload.UpdateDeviceUserDTO;
import flaviodeangeelis.u5w2d5.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping("")
    public Page<Device> getDevices(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size,
                                   @RequestParam(defaultValue = "id") String orderBy) {
        return deviceService.getDevices(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public Device findById(@PathVariable int id) {
        return deviceService.findById(id);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Device save(@RequestBody @Validated NewDeviceDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return deviceService.save(body);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        deviceService.findByIdAndDelete(id);
    }

    @PutMapping("/update/status/{id}")
    public Device findByIdAndUpdateStatus(@PathVariable int id, @RequestBody UpdateDeviceStatusDTO body) {
        return deviceService.findByIdAndUpdateStatus(id, body);

    }

    @PutMapping("/update/user/{id}")
    public Device findByIdAndUpdateUser(@PathVariable int id, @RequestBody UpdateDeviceUserDTO body) {
        return deviceService.findByIdAndUpdateUser(id, body);
    }
}

