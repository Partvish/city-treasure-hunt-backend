package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.EmailService;
import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.constants.dtos.UserDto;
import hu.bme.aut.treasurehunt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody UserDto userIn){
        Optional<User> u = userRepository.findById(userIn.name);
        if(u.isEmpty()){
            User user = new User();
            user.setEmail(userIn.email);
            user.setName(userIn.name);
            user.setRole(UserRole.Normal);
            user.setUuid(UUID.randomUUID());
            user.setEnabled(false);
            user.setPassword(passwordEncoder.encode(userIn.password));
            userRepository.save(user);
            emailService.sendMessage(user.getEmail(), "Regisztráció",
                    "<h1>Kedves Felhasználó!</h1> " +
                            "Ön regisztrált oldalunkon!" +
                            "<br/> Az alábbi linkre kattintva befejezheti a folyamatot és aktiválhatja a profilját: " +
                            "<a href=\"localhost:8080/activate/" + user.getUuid() +"\">link</a>");
            logger.debug("register: User registered with Email("+ userIn.email +")");
            return ResponseEntity.ok().build();
        }
        else {
            logger.debug("register: User registration failed. User already exists with Name("+ userIn.name +")");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("modify")
    @Secured(UserRole.Normal)
    public ResponseEntity<?> modify(Principal principal, @RequestBody UserDto userIn){
        Optional<User> u = userRepository.findById(principal.getName());
        if(u.isPresent()){
            User user = u.get();
            user.setName(userIn.name);
            if(!passwordEncoder.matches(u.get().getPassword(), user.getPassword())){
                user.setPassword(passwordEncoder.encode(userIn.password));
            }
            userRepository.save(user);
            emailService.sendMessage(user.getEmail(), "Profil Módosítás", "<h1>Kedves Felhasználó!</h1> " +" Ön módosította a profilját.");
            logger.debug("modfiy: User profile data modification was requested to User with Name(" + userIn.name +")");
            return ResponseEntity.ok().build();
        }
        else {
            logger.debug("modify: User profile data modification failed with Name("+userIn.name+")");
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("activate/{uuid}")
    public ResponseEntity<?> activate(@PathVariable UUID uuid){
        Optional<User> user = userRepository.findUserByUuid(uuid);
        if(user.isEmpty()){
            logger.debug("activate: User activation attempt with wrong uuid");
            return ResponseEntity.notFound().build();
        } else {
            User u = user.get();
            u.setEnabled(true);
            u.setUuid(null);
            userRepository.save(u);
            logger.debug("activate: User activated with Name(" + u.getEmail() + ")");
            return ResponseEntity.ok().build();
        }
    }
    @GetMapping("login")
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<?> login(Principal principal){
        logger.debug("login: User login done with Name("+principal.getName()+")");
        return ResponseEntity.ok(userRepository.findById(principal.getName()).get().getDto());
    }
}

