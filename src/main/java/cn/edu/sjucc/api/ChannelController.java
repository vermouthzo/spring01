package cn.edu.sjucc.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.sjucc.model.Channel;
import cn.edu.sjucc.model.Comment;
import cn.edu.sjucc.model.User;
import cn.edu.sjucc.service.ChannelService;

@RestController
@RequestMapping("/channel")
public class ChannelController {
	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@Autowired
	public CacheManager cacheManager;
	
	@Autowired
	private ChannelService service;
	
	@GetMapping
	public List<Channel> getAllChannels() {
		logger.info("正在查找所有频道信息：");
		List<Channel> results = service.getAllChannels();
		logger.debug("所有频道的数量是：" + results.size());
		return results;
	}
	
	@GetMapping("/{id}")
	public Channel getChannel(@PathVariable String id) {
		//logger.info("���ڶ�ȡ����Ƶ����Ϣ������");
		Channel c = service.getChannel(id);
		if(c != null) {
			return c;
		}else {
			logger.error("找不到指定的频道");
			return null;
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteChannel(@PathVariable String id) {
		System.out.println("即将删除频道，id="+id);
		boolean result = service.deleteChannel(id);
		if(result) {
			return ResponseEntity.ok().body("删除成功");
		}else {
			return ResponseEntity.ok().body("删除失败");
		}
	}
	
	@PostMapping
	public Channel createChannnel(@RequestBody Channel c) {
		System.out.println("即将新建频道:" +c);
		Channel saved = service.createChannel(c);
		return saved;
	}
	
	@PutMapping
	public Channel updateChannel(@RequestBody Channel c) {
		System.out.println("即将新建频道:" +c);
		Channel update = service.updateChannel(c);
		return update;
	}
	
	@GetMapping("/q/{quality}")
	public List<Channel> searchByQuality(@PathVariable String quality){
		return service.searchByQuality(quality);
	}
	
	@GetMapping("/t/{title}")
	public List<Channel> searchByTitle(@PathVariable String title){
		return service.searchByQuality(title);
	}
	
	@GetMapping("/cold")
	public List<Channel> getColdChannels(){
		return service.findColdChannels();
	}
	
	@GetMapping("p/page")
	public List<Channel> getChannelsPage(@PathVariable int page){
		return service.findChannelsPage(page);
	}
	
	@PostMapping("{channelId}/comment")
	public Channel addComment(@PathVariable String channelId, @RequestBody Comment comment) {
		Channel result = null;
		logger.debug("即将评论频道：" +channelId+ ",评论对象："+comment);
		//先检查用户是否登录过
		Cache cache = cacheManager.getCache(User.CACHE_NAME);
		ValueWrapper obj = cache.get("current_user");
		if(obj == null) {
			logger.warn("用户未登录！");
		} else {
			//把评论保存到数据库
			String username = (String) obj.get();
			logger.debug("登录用户"+username+"正在评论...");
			comment.setAuthor(username.toString());
			result = service.addComment(channelId, comment);
		}
		return result;
	}
	
	@GetMapping("{channelId}/hotcomments")
	public List<Comment> hotComments(@PathVariable String channelId){
		List<Comment> result = null;
		logger.debug("获取频道" +channelId+ "的热门评论");
		result = service.hotComments(channelId);
		return result;
	}
}