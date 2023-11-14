package flaviodeangeelis.u5w2d5.service;

import flaviodeangeelis.u5w2d5.entities.Role;
import flaviodeangeelis.u5w2d5.entities.User;
import flaviodeangeelis.u5w2d5.exception.BadRequestException;
import flaviodeangeelis.u5w2d5.exception.UnauthorizedException;
import flaviodeangeelis.u5w2d5.payload.LogInUserDTO;
import flaviodeangeelis.u5w2d5.payload.NewUserDTO;
import flaviodeangeelis.u5w2d5.repositories.UserRepository;
import flaviodeangeelis.u5w2d5.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String userAuthenticate(LogInUserDTO body) {
        User user = userService.findByEmail(body.email());

        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }

    public User save(NewUserDTO body) throws IOException {
        userRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });
        User newUser = new User();
        newUser.setUsername(body.username());
        newUser.setName(body.name());
        newUser.setSurname(body.surname());
        newUser.setEmail(body.email());
        newUser.setProfileImg("...");
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }
}
