package antifraud.services;

import antifraud.dto.UserDTO;
import antifraud.dto.mappers.MyUserMapper;
import antifraud.exceptions.UserAlreadyExistException;
import antifraud.exceptions.UserNotFoundException;
import antifraud.exceptions.UserNullPointerException;
import antifraud.models.User;
import antifraud.respositories.UserRepository;
import antifraud.utils.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final MyUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, MyUserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO saveUser(UserDTO userDTO) {
        if (Objects.isNull(userDTO)) {
            throw new UserNullPointerException();
        }
        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {
            throw new UserAlreadyExistException(userDTO.getUsername());
        }
        String encodePassword = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodePassword);
        User savedUser = userMapper.userDTOToUser(userDTO);
        // if random number is odd role will be ADMIN, otherwise USER
        Role userOrAdmin = Math.round(Math.random()) % 2 == 0 ? Role.USER : Role.ADMIN;
        savedUser.setRole(userOrAdmin);
        userRepository.save(savedUser);
        log.info("Saved user " + savedUser);
        return userMapper.userToUserDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users
               .stream()
               .map(userMapper::userToUserDTO)
               .collect(Collectors.toList());
    }

    public void removeUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        userRepository.delete(user);
        log.info("User with {} was deleted", username);
    }

}
