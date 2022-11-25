package hu.bme.aut.treasurehunt.service;

import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.model.constants.UserRole;
import hu.bme.aut.treasurehunt.model.dtos.UserDto;
import hu.bme.aut.treasurehunt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUser(long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUser(Principal principal) {
        return userRepository.findUserByEmail(principal.getName());
    }

    public Optional<User> createUser(UserDto userDto) {
        Optional<User> u = userRepository.findUserByName(userDto.name);
        if(u.isPresent())
            return Optional.empty();
        userDto.password = passwordEncoder.encode(userDto.password);
        userDto.role = UserRole.Normal;
        userDto.points = 0;
        User user = new User(userDto);
        logger.debug("create: User created");
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> modifyUser(Principal principal, UserDto userIn) {
        Optional<User> u = this.getUser(principal);

        if(u.isEmpty()) {
            return Optional.empty();
        }
        User user = u.get();
        user.setName(userIn.name);
        user.setEmail(userIn.email);
        if(!passwordEncoder.matches(u.get().getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(userIn.password));
        }
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> modifyUser(UserDto userIn) {
        Optional<User> u = this.userRepository.findUserByName(userIn.name);

        if(u.isEmpty()) {
            return Optional.empty();
        }
        User user = u.get();
        user.setName(userIn.name);
        user.setEmail(userIn.email);
        if(!passwordEncoder.matches(u.get().getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(userIn.password));
        }
        return Optional.of(userRepository.save(user));
    }

    public boolean deleteUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public void addPoint(User user, int point) {
        user.setPoints(user.getPoints()+point);
        userRepository.save(user);
    }
}
