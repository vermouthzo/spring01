package cn.edu.sjucc.service;

import org.omg.CORBA.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.sjucc.UserExistException;
import cn.edu.sjucc.api.UserController;
import cn.edu.sjucc.dao.UserRepository;
import cn.edu.sjucc.model.User;

@Service 
public class UserService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository repo;
	
	/**
	 * 新建用户 （用户注册）。
	 * @param user
	 * @return
	 */
	public User createUser(User user) throws UserExistException	{
		logger.debug("用户注册: " +user);
		User result = null;
		//TODO 1.保存前把用户密码加密
		//检查用户名是否已经存在， 如果存在则不允许注册
		User u = repo.findOneByUsername(user.getUsername());
		if (u != null) {	//说明用户已存在， 不允许注册
			throw new UserExistException();
		}
		result = repo.save(user);
		return result;
	}
	
	/**
	 * 检查用户名与密码是否匹配
	 * @param username	用户名
	 * @param password	用户密码
	 * @return	如果密码正确则返回true, 否则返回false
	 */
	public boolean checkUser(String username, String password) {
		boolean result = false;
		User u = repo.findOneByUsernameAndPassword(username, password);
		logger.debug("数据库中的用户信息是：" + u);
		if(null != u) {
			result = true;
		}
		return result;
	}
}
