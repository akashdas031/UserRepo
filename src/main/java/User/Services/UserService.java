package User.Services;

import java.util.List;

import User.DTOs.LoginRequestDto;
import User.Entities.UserEntity;
import User.Responses.LoginResponse;

public interface UserService {

	UserEntity createUser(UserEntity userEntity);
	LoginResponse LoginUser(LoginRequestDto userCredentials);
	List<UserEntity> getAllUsers();
	boolean deleteUser(String userId);
}
