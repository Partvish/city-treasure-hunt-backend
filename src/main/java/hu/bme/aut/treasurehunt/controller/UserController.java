package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


/***
 * Felhasználói adatokra szakosodott controller
 * /all összes user.
 * az api/user-re a bejelentkezett admin vagy felhasználó megkapja a saját adatait
 */

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/all")
    @Secured(UserRole.Admin)
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @GetMapping("{id}")
    @Secured(UserRole.Admin)
    public ResponseEntity<User> getById(@PathVariable String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(user.get());
    }

    @GetMapping
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<?> getOwnProfile(Principal principal){
        return ResponseEntity.ok(userRepository.findById(principal.getName()).get().getDto());
    }

    @PostMapping
    @Secured(UserRole.Admin)
    public User create(@RequestBody User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.debug("create: User created");
        return userRepository.save(user);
    }

    @PutMapping
    @Secured(UserRole.Admin)
    public ResponseEntity<User> update(@RequestBody User user){
        Optional<User> p = userRepository.findById(user.getEmail());
        if(p.isEmpty())
            return ResponseEntity.notFound().build();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.debug("update: User updated");
        return ResponseEntity.ok(userRepository.save(user));
    }

    @DeleteMapping("{email}")
    @Secured(UserRole.Admin)
    public ResponseEntity<?> delete(@PathVariable String email){
        Optional<User> user = userRepository.findById(email);
        if(user.isEmpty())
            return ResponseEntity.notFound().build();
        else {
            userRepository.deleteById(email);
            logger.debug("delete: User deleted with Id: " + email);
            return  ResponseEntity.ok().build();
        }
    }

}
