package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.service.AuthService;
import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

/***
 * Autorizációs függvényekre szakosodott controller
 * endpointok: login, modify, activate, register
 */

@RestController
@RequestMapping("/")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AuthService authService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDto userIn){
        Optional<User> user = authService.registerUser(userIn);
        if(user.isPresent()){
            logger.debug("register: User registered with Email("+ userIn.email +")");
            return ResponseEntity.ok(user.get().getDto());
        }
        else {
            logger.debug("register: User registration failed. User already exists with Name("+ userIn.name +")");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("modify")
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<?> modify(Principal principal, @RequestBody UserDto userIn){
        if(authService.modifyUser(principal, userIn)){
            logger.debug("modfiy: User profile data modification was requested to User with Name(" + userIn.name +")");
            return ResponseEntity.ok().build();
        }
        else {
            logger.debug("modify: User profile data modification failed with Name("+userIn.name+")");
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("login")
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<?> login(Principal principal){
        Optional<User> user = authService.loginUser(principal);
        if(user.isPresent())
            return ResponseEntity.ok(user.get().getDto());
        else
            return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }
}

