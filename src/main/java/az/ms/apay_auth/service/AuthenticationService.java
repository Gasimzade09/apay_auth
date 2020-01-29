package az.ms.apay_auth.service;


import az.ms.apay_auth.dao.UserDao;
import az.ms.apay_auth.exception.AuthenticationException;
import az.ms.apay_auth.mapper.UserMapper;
import az.ms.apay_auth.model.Role;
import az.ms.apay_auth.model.dto.JwtAuthenticationRequest;
import az.ms.apay_auth.model.dto.JwtAuthenticationResponse;
import az.ms.apay_auth.model.dto.UserDto;
import az.ms.apay_auth.model.entity.UserEntity;
import az.ms.apay_auth.util.TokenUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;

@Service
public class AuthenticationService {

    private final TokenUtils tokenUtils;

    private final AuthenticationManager authenticationManager;

    private final UserDao userDao;

    public AuthenticationService(TokenUtils tokenUtils,
                                 AuthenticationManager authenticationManager,
                                 UserDao userRepository) {
        this.tokenUtils = tokenUtils;
        this.authenticationManager = authenticationManager;
        this.userDao = userRepository;
    }


    public JwtAuthenticationResponse createAuthenticationToken(JwtAuthenticationRequest request) {
        authenticate(request.getUsername(), request.getPassword());
        String token = tokenUtils.generateToken(request.getUsername());
        return new JwtAuthenticationResponse(token);
    }

    public void authenticate(String username, String password) {

        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials", e);
        }
    }

    public boolean emailValidation(String email){
        boolean valid = email.matches("^[A-z0-9._%+-]+@[A-z0-9.-]+\\.[A-z]{2,6}$");
        return valid;
    }

    public String passwordGenerator(){
        Random random = new Random();
        int len = (int) (Math.random()*10);
        return random.ints(48, 122)
                .filter(t -> (t < 57 || t > 65) && (t < 90 || t > 97))
                .mapToObj(t -> (char) t)
                .limit(10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public UserDto signUp(UserDto user){
        UserEntity checkEmail = userDao.getByEmail(user.getEmail());
        if (user.getEmail()==null || user.getName()==null ||
                user.getPwd() == null || user.getPhoneNumber() == null || user.getSurname() == null)
            throw new AuthenticationException("Fields username, email, phone number, name, surname must not be null");
        if (checkEmail == null && emailValidation(user.getEmail())){
            String password = new BCryptPasswordEncoder().encode(user.getPwd());
            UserEntity userEntity = UserMapper.INSTANCE.toEntity(user);
            userEntity.setRole(Role.ROLE_USER.toString());
            userEntity.setPassword(password);
            userDao.save(userEntity);
            return UserMapper.INSTANCE.toDto(userEntity);
        } else {
            throw new AuthenticationException("This email is already exists");
        }
    }

    public String checkingUser(){
        Integer id = userDao.getByEmail(TokenUtils.getCurrentUserLogin()).getId();
        return id.toString();
    }
}
