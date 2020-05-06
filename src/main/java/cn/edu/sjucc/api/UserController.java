package cn.edu.sjucc.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.sjucc.model.User;
import cn.edu.sjucc.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService service;
	
	@PostMapping("/register")
	public User register(@RequestBody User u) {
		logger.debug("即将注册用户，用户数据：" +u);
		User saved = service.createUser(u);
		return saved;
	}
}
