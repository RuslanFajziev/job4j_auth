package ru.job4j.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.job4j.domain.Login;
import ru.job4j.repository.LoginRepository;

import static java.util.Collections.emptyList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private LoginRepository loginRep;

    public UserDetailsServiceImpl(LoginRepository loginRep) {
        this.loginRep = loginRep;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> optionalLogin = loginRep.findByUsername(username);
        if (optionalLogin.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        
        Login login = optionalLogin.get();
        return new User(login.getUsername(), login.getPassword(), emptyList());
    }
}