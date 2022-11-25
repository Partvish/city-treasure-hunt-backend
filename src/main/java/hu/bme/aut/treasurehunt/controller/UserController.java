package hu.bme.aut.treasurehunt.controller;

import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.UserDto;
import hu.bme.aut.treasurehunt.repository.UserRepository;
import hu.bme.aut.treasurehunt.service.UserService;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/all")
    @Secured(UserRole.Admin)
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{id}")
    @Secured(UserRole.Admin)
    public ResponseEntity<User> getById(@PathVariable Long id){
        Optional<User> user = userService.getUser(id);
        if(user.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(user.get());
    }

    @GetMapping
    @Secured({UserRole.Admin, UserRole.Normal})
    public ResponseEntity<?> getOwnProfile(Principal principal){
        return ResponseEntity.ok(userService.getUser(principal));
    }

    @PostMapping
    @Secured(UserRole.Admin)
    public ResponseEntity<User> create(@RequestBody UserDto userDto){
        Optional<User> user = userService.createUser(userDto);
        if(user.isPresent()){
            return ResponseEntity.ok(user.get());
        }
        else{
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping
    @Secured(UserRole.Admin)
    public ResponseEntity<User> update(@RequestBody UserDto user){
        Optional<User> u = userService.modifyUser(user);
        if(u.isPresent())
            return ResponseEntity.ok(u.get());
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    @Secured(UserRole.Admin)
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(userService.deleteUser(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
