package antifraud.services;

import antifraud.dto.UserAccessDTO;
import antifraud.dto.UserDTO;
import antifraud.dto.UserRoleDTO;
import antifraud.dto.mappers.MyUserMapper;
import antifraud.exceptions.*;
import antifraud.entities.User;
import antifraud.respositories.UserRepository;
import antifraud.utils.Operation;
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
            throw new EntityNullPointerException();
        }
        if (userRepository.findUserByUsername(userDTO.getUsername()).isPresent()) {
            throw new EntityAlreadyExistException(userDTO.getUsername());
        }
        String encodePassword = passwordEncoder.encode(userDTO.getPassword());
        User savedUser = userMapper.userDTOToUser(userDTO);
        Role role;
        if(userRepository.count() == 0) {
            role = Role.ADMINISTRATOR;
        } else {
            role = Role.MERCHANT;
        }
        savedUser.setPassword(encodePassword);
        savedUser.setRole(role);
        savedUser.setLocked(role != Role.ADMINISTRATOR);
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
                .orElseThrow(() -> new EntityNotFoundException(username));
        userRepository.delete(user);
        log.info("User with {} was deleted", username);
    }

    public UserDTO updateRoleByUsername(UserRoleDTO userRoleDTO) {
        String username = userRoleDTO.getUsername();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(username));
        Role roleForUpdate = Role.valueOf(userRoleDTO.getRole());
        if (!List.of(Role.MERCHANT, Role.SUPPORT).contains(roleForUpdate)) {
            throw new UnsupportedRoleException(roleForUpdate.name());
        }
        if (roleForUpdate == user.getRole()) {
            throw new EntityAlreadyExistException(roleForUpdate);
        }
        user.setRole(roleForUpdate);
        userRepository.save(user);
        log.info("User role was updated {} ", user);
        return userMapper.userToUserDTO(user);
    }

    public boolean updateAccessByUsername(UserAccessDTO userAccessDTO) {
        String username = userAccessDTO.getUsername();
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(username));
        if (user.getRole() == Role.ADMINISTRATOR) {
            throw new UnsupportedRoleException("User with " + Role.ADMINISTRATOR.name() + " role " + "can't be locked");
        }
        String stringAccess = userAccessDTO.getOperation();
        Operation accessOperation = Operation.valueOf(stringAccess);
        boolean isLocked = accessOperation == Operation.LOCK;
        user.setLocked(isLocked);
        userRepository.save(user);
        log.info("User access was updated {} ", user);
        return isLocked;
    }

}
