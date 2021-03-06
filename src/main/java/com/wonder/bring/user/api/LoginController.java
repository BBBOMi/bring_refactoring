package com.wonder.bring.user.api;

import com.wonder.bring.user.api.dto.LoginRequest;
import com.wonder.bring.config.security.auth.service.AuthService;
import com.wonder.bring.config.security.auth.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wonder.bring.common.dto.DefaultResponse.FAIL_DEFAULT_RESPONSE;


@Slf4j
@RequestMapping("login")
@RestController
public class LoginController {

    private final AuthService authService;

    public LoginController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("")
    public ResponseEntity login(@RequestBody final LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Auth
    @GetMapping("")
    public ResponseEntity checkToken() {
        try {
            return new ResponseEntity<>(authService.checkToken(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RESPONSE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
