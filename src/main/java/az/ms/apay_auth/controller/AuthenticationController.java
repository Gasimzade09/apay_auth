package az.ms.apay_auth.controller;

import az.ms.apay_auth.constants.Endpoints;
import az.ms.apay_auth.model.dto.JwtAuthenticationRequest;
import az.ms.apay_auth.model.dto.JwtAuthenticationResponse;
import az.ms.apay_auth.model.dto.UserDto;
import az.ms.apay_auth.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(Endpoints.API)
@RestController
public class AuthenticationController {

    private final AuthenticationService service;


    public AuthenticationController(AuthenticationService service) {
        this.service = service;

    }

    @PostMapping(Endpoints.AUTH)
    public JwtAuthenticationResponse signIn(@RequestBody JwtAuthenticationRequest request) {
        return service.createAuthenticationToken(request);
    }

    @PostMapping(Endpoints.USER_REG)
    public UserDto reg(@RequestBody UserDto user){
        return service.signUp(user);
    }

    @PostMapping(Endpoints.USER_INFO)
    public String info(){
        return service.checkingUser();
    }
}