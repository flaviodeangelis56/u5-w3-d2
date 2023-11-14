package flaviodeangeelis.u5w2d5.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import flaviodeangeelis.u5w2d5.entities.User;
import flaviodeangeelis.u5w2d5.exception.NotFoundException;
import flaviodeangeelis.u5w2d5.payload.UpdateUserDTO;
import flaviodeangeelis.u5w2d5.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;


    public Page<User> getUsers(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return userRepository.findAll(pageable);
    }

    public User findById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public User findByIdAndUpdate(int id, UpdateUserDTO body) {
        User found = userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));

        found.setUsername(body.username());
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setEmail(body.email());
        found.setProfileImg(found.getProfileImg());
        return userRepository.save(found);
    }


    public void findByIdAndDelete(int id) throws NotFoundException {
        User found = this.findById(id);
        userRepository.delete(found);
    }

    public String uploadImg(MultipartFile file, int id, User body) throws IOException {
        User found = this.findById(id);
        String imgURL = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setId(id);
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setEmail(body.getEmail());
        found.setUsername(body.getUsername());
        found.setProfileImg(imgURL);
        userRepository.save(found);
        return imgURL;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }
}
