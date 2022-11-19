package hu.bme.aut.treasurehunt.auth;

import hu.bme.aut.treasurehunt.domain.User;
import hu.bme.aut.treasurehunt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(s);
        if(user.isEmpty())
            throw new UsernameNotFoundException(s + " is an invalid username");
        else {
            return new UserDetailsImpl(user.get());
        }
    }


}
