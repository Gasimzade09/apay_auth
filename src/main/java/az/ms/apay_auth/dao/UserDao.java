package az.ms.apay_auth.dao;

import az.ms.apay_auth.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Integer> {
    UserEntity getByEmail(String email);
}
