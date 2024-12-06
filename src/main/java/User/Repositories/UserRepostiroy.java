package User.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import User.Entities.UserEntity;

public interface UserRepostiroy extends JpaRepository<UserEntity, String>{

	Optional<UserEntity> findByEmail(String email);
}
