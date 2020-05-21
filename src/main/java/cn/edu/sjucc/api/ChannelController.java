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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.sjucc.model.Channel;
import cn.edu.sjucc.model.Comment;
import cn.edu.sjucc.model.Result;
import cn.edu.sjucc.model.User;
import cn.edu.sjucc.service.ChannelService;
import cn.edu.sjucc.service.UserService;

@RestController
@RequestMapping("/channel")
public class ChannelController {
	public static final Logger logger = LoggerFactory.getLogger(ChannelController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChannelService service;
	
	@GetMapping
	public Result<List<Channel>> getAllChannels() {
		logger.info("正在查找所有频道信息：");
		Result<List<Channel>> result = new Result<List<Channel>>();
		List<Channel> channels = service.getAllChannels();
		result.setStatus(Result.OK);
		result.setMessage("所有频道信息");
		result.setData(channels);
		return result;
	}
	
	@GetMapping("/{id}")
	public Result<Channel> getChannel(@PathVariable String id) {
		logger.info("正在读取频道："+id);
		Result <Channel> result = new Result<>();
		Channel c = service.getChannel(id);
		if(c != null) {
			result.setStatus(Result.OK);
			result.setMessage("找到一个频道");
			result.setData(c);
		}else {
			logger.error("找不到指定的频道");
			result.setStatus(Result.ERROR);
			result.setMessage("找不到指定的频道");
		}
		return result;
	}
	
	@DeleteMapping("/{id}")
	public Result<Channel> deleteChannel(@PathVariable String id) {
		logger.info("即将删除频道，id="+id);
		Result<Channel> result = new Result<>();
		boolean del = service.deleteChannel(id);
		if(del) {
			result.setStatus(Result.OK);
			result.setMessage("删除成功");
		}else {
			result.setStatus(Result.ERROR);
			result.setMessage("删除失败");
		}
		return result;
	}
	
	@PostMapping
	public Result<Channel> createChannnel(@RequestBody Channel c) {
		logger.info("即将新建频道:" +c);
		Result<Channel> result = new Result<>();
		Channel saved = service.createChannel(c);
		result.setStatus(Result.OK);
		result.setMessage("新建频道成功");
		result.setData(saved);
		return result;
	}
	
	@PutMapping
	public Result<Channel> updateChannel(@RequestBody Channel c) {
		logger.info("即将新建频道:" +c);
		Result<Channel> result = new Result<>();
		Channel update = service.updateChannel(c);
		result.setStatus(Result.OK);
		result.setMessage("更新频道成功");
		result.setData(update);
		return result;
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
	public Channel addComment(@RequestHeader("token") String token, @PathVariable String channelId, @RequestBody Comment comment) {
		Channel result = null;
		//把评论保存到数据库
		String username = userService.currentUser(token);
		logger.debug("登录用户"+username+"正在评论...");
		comment.setAuthor(username);
		result = service.addComment(channelId, comment);
				
		return result;
	}
	
	@GetMapping("{channelId}/hotcomments")
	public Result<List<Comment>> hotComments(@PathVariable String channelId){
		Result<List<Comment>> result = new Result<>();
		logger.debug("获取频道" +channelId+ "的热门评论");
		result.setStatus(Result.OK);
		result.setMessage("更新频道成功");
		result.setData(service.hotComments(channelId));
		return result;
	}
}