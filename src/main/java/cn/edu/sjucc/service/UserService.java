package cn.edu.sjucc.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.sjucc.api.UserController;
import cn.edu.sjucc.dao.UserRepository;
import cn.edu.sjucc.model.User;

@Service 
public class UserService {
	@Autowired
	private UserRepository repo;
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	/**
	 * 新建用户 （用户注册）。
	 * @param user
	 * @return
	 */
	public User createUser(User user) {
		logger.debug("用户注册: " +user);
		User result = null;
		result = repo.save(user);
		return result;
	}
}
