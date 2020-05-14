package cn.edu.sjucc.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cn.edu.sjucc.dao.ChannelRepository;
import cn.edu.sjucc.model.Channel;
import cn.edu.sjucc.model.Comment;

@Service
public class ChannelService {
	public static final Logger logger = LoggerFactory.getLogger(ChannelService.class);
	@Autowired
	private ChannelRepository repo;
	
	/**
	 * 获取所有频道的数据
	 * 
	 * @return	频道List
	 */
	@Cacheable(cacheNames="channels", key="'all_channesls'")
	public List<Channel> getAllChannels(){
		logger.debug("从数据库中读取所有频道信息...");
		return repo.findAll();
	}
	
	/**
	 * 获取一个频道的数据
	 * 
	 * @param channelId 频道编号
	 * @return 频道对象,若未找到则返回null
	 */
	@Cacheable(cacheNames="channels", key="#channelId")
	public Channel getChannel(String channelId) {
		logger.debug("从数据库中读取频道:"+channelId);
		Optional<Channel> result = repo.findById(channelId);
		
		if(result.isPresent()){
			return result.get();
		}else {
			return null;
		}
	}
	
	/**
	 * 删除指定的频道
	 * 
	 * @param channelId 待删除的频道编号
	 * @return 若删除成功则返回true，否则返回false
	 */
	public boolean deleteChannel(String channelId){
		boolean result = true;
		repo.deleteById(channelId);
		return result;
	}
	
	/**
	 * 保存频道
	 * 
	 * @param c 待保存的频道对象（没有id值）
	 * @return 保存后的频道（有id值）
	 */
	@CachePut(cacheNames="channels", key="#result.id")
	public Channel createChannel(Channel c){
		logger.debug("从数据库中读取所有频道信息...");
		return repo.save(c); //保存更新后的实体对象
	}
	
	/**
	 * 更新指定的频道信息
	 * 
	 * @param c 新的频道信息，用于更新已存在的同一频道
	 * @return 更新后的频道信息
	 */
	@CacheEvict(cacheNames="channels", key="'all_channesls'")
	public Channel updateChannel(Channel c) {
		//仅修改用户指定的属性
		Channel saved = getChannel(c.getId());
		if (c.getTitle() != null) {
			saved.setTitle(c.getTitle());
		}
		if(c.getQuality() != null) {
			saved.setQuality(c.getQuality());
		}
		if (c.getUrl() != null) {
			saved.setUrl(c.getUrl());
		}
		if (c.getCover() != null) {
			saved.setCover(c.getCover());
		}
		// FIXME 把新评论追加到老评论后
		if (c.getComments() != null) {
			if(saved.getComments() != null) {
				saved.getComments().addAll(c.getComments());
			}else{
				saved.setComments(c.getComments());
			}
		}
		return repo.save(saved);
	}
	
	public List<Channel> searchByQuality(String quality){
		return repo.findByQuality(quality);
	}
	
	public List<Channel> searchByTitle(String title){
		return repo.findByQuality(title);
	}
	
	/**
	 * 获取冷门频道
	 * @return
	 */
	public List<Channel> findColdChannels(){
		return repo.findByCommentsNull();
	}
	
	public List<Channel> findChannelsPage(int page){
		Page<Channel> p = repo.findAll(PageRequest.of(page,3));
		return p.toList();
	}
	
	/**
	 * 向指定频道追加一条评论
	 * @param channelId	目标评论的编号
	 * @param comment	即将添加的评论
	 */
	public Channel addComment(String channelId, Comment comment) {
		Channel result = null;
		Channel saved = getChannel(channelId);
		if (null != saved) {	//数据中有该评论
			saved.addComment(comment);
			result = repo.save(saved);
		}
		return result;
	}
	
	/**
	 * 获取目标频道的热门评论。
	 * @param channelId
	 * @return
	 */
	public List<Comment> hotComments(String channelId){
		List<Comment> result = null;
		Channel saved = getChannel(channelId);
		if(saved != null) {
				result = saved.getComments();
				result.sort(new Comparator<Comment>() {
				@Override
				public int compare(Comment o1, Comment o2) {
					//若o1比o2小，则返回负数；若o1比o2大，则返回正数；若o1等于o2，则返回0。
					int re = 0;
					if (o1.getStar() > o2.getStar()) {
						re = -1;
					}else if(o1.getStar() < o2.getStar()) {
						re = 1;
					}
					return re;
				}
			});
			if (result.size()>3) {
				result = result.subList(0, 3);
			}
			logger.debug("热门评论有" +result.size()+ "条...");
			logger.debug(result.toString());
		}else {
			logger.warn("指定的频道不存在，id=" +channelId);
		}
		return result;
	}
}
