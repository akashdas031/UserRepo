package User.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import User.Entities.UserEntity;
import User.Services.UserService;

@RestController
@RequestMapping("user/v1/")
public class UserController {

	@Autowired
	private UserService userServ;
	
	@PreAuthorize("permitAll()")
	@PostMapping("/registerUser")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
		UserEntity createdUser = this.userServ.createUser(user);
		return new ResponseEntity<UserEntity>(createdUser,HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole(ROLE_ADMIN)")
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserEntity>> getAllUsers(){
		List<UserEntity> allUsers = this.userServ.getAllUsers();
		return new ResponseEntity<List<UserEntity>>(allUsers,HttpStatus.OK);
	}
}
