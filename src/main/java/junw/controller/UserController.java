package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.entity.User;
import junw.service.UserService;
import junw.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-02-17-45  星期三
 * @description
 */
@RestController
@Slf4j
@Api(tags = "用户相关接口")
@RequestMapping
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private RedisTemplate redisTemplate;
	// 调用redis服务，将我们的验证码缓存进去

	/**
	 * 发送验证码
	 *
	 * @param user        用户信息
	 * @param httpSession session
	 * @return 结果
	 */
	@PostMapping("/sendMsg")
	@ApiOperation("发送验证码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "user", value = "user实体", required = true),
			@ApiImplicitParam(name = "httpSession", value = "session")
	})
	public ReturnResult<String> sendMsg(@RequestBody User user, HttpSession httpSession) {
		String userPhone = user.getPhone();
		if (userPhone != null) {
			String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();
			log.info("目前的验证码是：" + validateCode);
			// =================================================================
			// 没有购买下面的阿里云服务，直接注释掉
			// =================================================================
			// SMSUtils.sendMessage("外卖", "", userPhone, validateCode);
			// 短信签名
			// 短信的模板代码
			// 用户手机号，随机出来的验证码
			// httpSession.setAttribute(userPhone, validateCode);
			// =================================================================
			// 项目优化：将验证码缓存到redis中
			// 因为我们的验证码保存到缓存中，所以也就不需要上面的session来保存
			redisTemplate.opsForValue().set(userPhone, validateCode, 5, TimeUnit.MINUTES);
			return ReturnResult.sendSuccess("发送成功");
		}
		return ReturnResult.sendError("发送失败");
	}

	/**
	 * 登录接口
	 *
	 * @param map         map
	 * @param httpSession session
	 * @return 用户实体
	 */
	@PostMapping("/login")
	@ApiOperation("登录接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "map", value = "map实体", required = true),
			@ApiImplicitParam(name = "httpSession", value = "session")
	})
	public ReturnResult<User> login(@RequestBody Map map, HttpSession httpSession) {
		String phone = map.get("phone").toString();
		String code = map.get("code").toString();
		// Object attribute = httpSession.getAttribute(phone);// 获取缓存的验证码
		// 如果已经登录成功，那就把缓存中的验证码给删除
		// =======================================================================
		// 和上面同理，直接从缓存中读取即可
		Object attribute = redisTemplate.opsForValue().get(phone);// 从redis中获取的验证码
		// =======================================================================
		if (attribute != null && attribute.equals(code)) {
			LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(User::getPhone, phone);
			User one = userService.getOne(lambdaQueryWrapper);// 需要完成判空操作
			if (one == null) {// 如果没有该用户，那么就注册一个
				one = new User();
				one.setPhone(phone);
				one.setStatus(1);
				userService.save(one);
			}
			// 用户登录成功以后，将验证码删除
			redisTemplate.delete(phone);// 将验证码删除
			// =======================================================================
			return ReturnResult.sendSuccess(one);
		}
		return ReturnResult.sendError("发送失败");
	}
}
