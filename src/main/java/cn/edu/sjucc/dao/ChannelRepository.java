package cn.edu.sjucc.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import cn.edu.sjucc.model.Channel;

@Repository
public interface ChannelRepository extends MongoRepository<Channel,String> {
	
	/**
	 * 找出没有评论的频道
	 * @return
	 */
	public List<Channel> findByCommentsNull();
		
	public List<Channel> findByQuality(String q);
	
	public List<Channel> findByTitle(String t);
	
	
}
