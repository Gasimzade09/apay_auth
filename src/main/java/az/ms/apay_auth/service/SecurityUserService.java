package az.ms.apay_auth.service;

import az.ms.apay_auth.dao.UserDao;
import az.ms.apay_auth.model.Role;
import az.ms.apay_auth.model.SecurityUser;
import az.ms.apay_auth.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SecurityUserService implements UserDetailsService {

    private final UserDao userDao;

    public SecurityUserService(UserDao repository) {
        this.userDao = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userDao.getByEmail(email);

        return buildSecurityUser(user);
    }

    private SecurityUser buildSecurityUser(UserEntity user) {
        return SecurityUser.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(toSecurityRole(user.getRole())))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true).build();
    }

    private Role toSecurityRole(String role) {
        Role enumRole = null;
        try {
            enumRole = enumRole.valueOf(role);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enumRole;

    }
}