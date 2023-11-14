package flaviodeangeelis.u5w2d5.controller;

import flaviodeangeelis.u5w2d5.entities.User;
import flaviodeangeelis.u5w2d5.exception.BadRequestException;
import flaviodeangeelis.u5w2d5.exception.NotFoundException;
import flaviodeangeelis.u5w2d5.payload.UpdateUserDTO;
import flaviodeangeelis.u5w2d5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService usersService;

    @GetMapping("")
    public Page<User> getUser(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size,
                              @RequestParam(defaultValue = "id") String orderBy) {
        return usersService.getUsers(page, size, orderBy);
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable int id) {
        return usersService.findById(id);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIdAndUpdate(@PathVariable int id, @RequestBody @Validated UpdateUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return usersService.findByIdAndUpdate(id, body);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        usersService.findByIdAndDelete(id);
    }

    @PutMapping("/update/img/{id}")
    public String uploadImg(@RequestParam("avatar") MultipartFile file, @PathVariable int id) throws IOException {
        User found = usersService.findById(id);
        return usersService.uploadImg(file, id, found);
    }


    @GetMapping("/me")
    public UserDetails getCurrentProfile(@AuthenticationPrincipal UserDetails currentUser) {
        return currentUser;
    }

    @DeleteMapping("/me")
    public void deleteCurrent(@AuthenticationPrincipal User currentUser) {
        usersService.findByIdAndDelete(currentUser.getId());
    }
}
