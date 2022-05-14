package antifraud.controllers;

import antifraud.dto.UserAccessDTO;
import antifraud.dto.UserDTO;
import antifraud.dto.UserRoleDTO;
import antifraud.dto.UserStatusDTO;
import antifraud.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("Incoming user " + userDTO);
        UserDTO savedUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<UserStatusDTO> delete(@PathVariable String username) {
       userService.removeUserByUsername(username);
       return new ResponseEntity<>(new UserStatusDTO(username, "Deleted successfully!"), HttpStatus.OK);
    }

    @PutMapping("/role")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserRoleDTO userRoleDTO) {
        log.info("Incoming: {}", userRoleDTO);
        UserDTO updatedUser = userService.updateRoleByUsername(userRoleDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PutMapping("/access")
    public ResponseEntity<UserStatusDTO> update(@RequestBody @Valid UserAccessDTO userAccessDTO) {
        log.info("Incoming user: " + userAccessDTO);
        String username = userAccessDTO.getUsername();
        UserStatusDTO userStatusDTO = userService.updateAccessByUsername(userAccessDTO)
                ? new UserStatusDTO.UserLocked(username)
                : new UserStatusDTO.UserUnlocked(username);
        return new ResponseEntity<>(userStatusDTO, HttpStatus.OK);
    }


}
