package User.ServiceImpls;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import User.DTOs.LoginRequestDto;
import User.Entities.UserEntity;
import User.Enums.Role;
import User.Exceptions.UserNotFoundException;
import User.Repositories.UserRepostiroy;
import User.Responses.LoginResponse;
import User.SecurityConfiguration.JwtUtil;
import User.Services.UserService;

@Service
public class UserServiceImpl implements UserService{

	
	private final UserRepostiroy userRepo;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtUtil jwtUtil;
	 public UserServiceImpl(UserRepostiroy userRepo, PasswordEncoder passwordEncoder,JwtUtil jwtUtil) {
		 this.userRepo=userRepo;
		 this.passwordEncoder=passwordEncoder;
		 this.jwtUtil=jwtUtil;
	 }
	
	@Override
	public UserEntity createUser(UserEntity userEntity) {
		String uid=UUID.randomUUID().toString().substring(0,10).replace('-', ' ');
		UserEntity user = UserEntity.builder().id(uid).email(userEntity.getEmail()).username(userEntity.getUsername())
		.password(passwordEncoder.encode(userEntity.getPassword())).role(Role.USER).build();
		return this.userRepo.save(user);
		
	}

	@Override
	public LoginResponse LoginUser(LoginRequestDto userCredentials) {
		UserEntity findByEmail = userRepo.findByEmail(userCredentials.getEmail()).orElseThrow(()->new UserNotFoundException("User with Given Email Doesn't Exist...Please check the Email and try again"));
		if(!passwordEncoder.matches(userCredentials.getPassword(), findByEmail.getPassword())) {
			throw new UserNotFoundException("Please check your Password and try again");
		}
		String token=jwtUtil.generateToken(userCredentials.getEmail());
		return LoginResponse.builder().userEntity(findByEmail).token(token).build();
	}

	@Override
	public List<UserEntity> getAllUsers() {
		
		return this.userRepo.findAll();
	}

	@Override
	public boolean deleteUser(String userId) {
		this.userRepo.deleteById(userId);
		return this.userRepo.existsById(userId);
	}

}
