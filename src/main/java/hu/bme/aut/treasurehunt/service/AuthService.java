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
import java.util.Optional;

@Service
public class AuthService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    public Optional<User> registerUser(UserDto userIn){
        return userService.createUser(userIn);
    }

    public boolean modifyUser(Principal principal, UserDto userIn){
        Optional<User> user = userService.modifyUser(principal, userIn);
        return user.isPresent();
    }

    public Optional<User> loginUser(Principal principal) {
        logger.debug("login: User login done with Name("+principal.getName()+")");
        return userService.getUser(principal);
    }

    public String getRole(Principal principal){
        Optional<User> user = userService.getUser(principal);
        if(user.isEmpty())
            return "";
        if(user.get().getRole().equals(UserRole.Admin))
            return UserRole.Admin;
        return UserRole.Normal;
    }
}
