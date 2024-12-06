package User.Controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import User.DTOs.LoginRequestDto;
import User.Entities.UserEntity;
import User.Responses.LoginResponse;
import User.Services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userServ;
	@PreAuthorize("permitAll()")
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequestDto credentails){
		LoginResponse user = this.userServ.LoginUser(credentails);
		return new ResponseEntity<LoginResponse>(user,HttpStatus.OK);
	}
	@PreAuthorize("permitAll()")
	@PostMapping("/registerUser")
	public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user){
		UserEntity createdUser = this.userServ.createUser(user);
		return new ResponseEntity<UserEntity>(createdUser,HttpStatus.CREATED);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserEntity>> getAllUsers(){
		List<UserEntity> allUsers = this.userServ.getAllUsers();
		return new ResponseEntity<List<UserEntity>>(allUsers,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<Map<String,String>> deleteUser(@PathVariable("userId") String userId){
		boolean deletedUser = this.userServ.deleteUser(userId);
		Map<String,String> map=new HashMap<>();
		if(!deletedUser) {
			map.put("status", "Success");
			map.put("Deleted User Id", userId);
			map.put("message", "user with Id "+userId+" has been deleted Successfully");
			return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
		}else {
			map.put("status", "Failure");
			map.put("message", "user with Id "+userId+" has not been deleted...Something went wrong!!!");
			return new ResponseEntity<Map<String,String>>(map,HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
}
