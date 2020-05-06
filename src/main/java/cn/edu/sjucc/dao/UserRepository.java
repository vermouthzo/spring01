package cn.edu.sjucc.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import cn.edu.sjucc.model.User;

public interface UserRepository extends MongoRepository<User, String> {
	
}
