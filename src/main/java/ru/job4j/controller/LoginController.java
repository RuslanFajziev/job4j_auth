package ru.job4j.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.job4j.domain.Login;
import ru.job4j.repository.LoginRepository;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/logins")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class.getSimpleName());

    private LoginRepository loginRep;
    private BCryptPasswordEncoder cryptPasswordEncoder;

    public LoginController(LoginRepository loginRep, BCryptPasswordEncoder cryptPasswordEncoder) {
        this.loginRep = loginRep;
        this.cryptPasswordEncoder = cryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Login> signUp(@RequestBody Login login) {
        LOG.info("Create login={}", login);
        login.setPassword(cryptPasswordEncoder.encode(login.getPassword()));
        return new ResponseEntity<Login>(
                this.loginRep.save(login),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public List<Login> findAll() {
        LOG.info("Get All logins");
        List<Login> lstLogin = new ArrayList<>();
        Iterable<Login> loginIterable = loginRep.findAll();
        for (Login login : loginIterable) {
            lstLogin.add(login);
        }
        return lstLogin;
    }
}
