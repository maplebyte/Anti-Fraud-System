package antifraud.controllers;

import antifraud.dto.UserAccessDTO;
import antifraud.dto.UserDTO;
import antifraud.dto.UserRoleDTO;
import antifraud.dto.UserStatusDTO;
import antifraud.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Controller", description = "Controller provides the HTTP Basic authentication, enables to retrieve a list of users and(>>>) manupulate data.")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Saves a user")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a SuspiciousIpDTO"),
            @ApiResponse(responseCode = "201", description = "Successfully saved"),
            @ApiResponse(responseCode = "409", description = "The user is already in database"),
    })
    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("Incoming user " + userDTO);
        UserDTO savedUser = userService.saveUser(userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @Operation(summary = "Gets all users")
    @ApiResponses(value = {
            @ApiResponse(description = "List<UserDTO>"),
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
    })
    @GetMapping("/list")
    public ResponseEntity<List<UserDTO>> getAll() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Deletes a user")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a UserStatusDTO"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "The user not found"),
    })
    @DeleteMapping("/user/{username}")
    public ResponseEntity<UserStatusDTO> delete(@PathVariable String username) {
       userService.removeUserByUsername(username);
       return new ResponseEntity<>(new UserStatusDTO(username, "Deleted successfully!"), HttpStatus.OK);
    }

    @Operation(summary = "Allows a user with the ADMINISTRATOR role to change user roles")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a UserDTO"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "409", description = "The role is already assigned"),
            @ApiResponse(responseCode = "404", description = "The user not found"),
            @ApiResponse(responseCode = "400", description = "The role is not SUPPORT or MERCHANT"),
    })
    @PutMapping("/role")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid UserRoleDTO userRoleDTO) {
        log.info("Incoming: {}", userRoleDTO);
        UserDTO updatedUser = userService.updateRoleByUsername(userRoleDTO);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @Operation(summary = "Allows a user with the ADMINISTRATOR role to lock/unlock users")
    @ApiResponses(value = {
            @ApiResponse(description = "Returns a UserDTO"),
            @ApiResponse(responseCode = "200", description = "Successfully deleted"),
            @ApiResponse(responseCode = "404", description = "The user not found"),
            @ApiResponse(responseCode = "400", description = "The user with role ADMINISTRATOR cannot be blocked"),
    })
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
