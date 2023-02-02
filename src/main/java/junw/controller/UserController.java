package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import junw.common.ReturnResult;
import junw.entity.User;
import junw.service.UserService;
import junw.utils.SMSUtils;
import junw.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

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
@RequestMapping
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/sendMsg")
	public ReturnResult<String> sendMsg(@RequestBody User user, HttpSession httpSession) {
		String userPhone = user.getPhone();
		if (userPhone != null) {
			String validateCode = ValidateCodeUtils.generateValidateCode(6).toString();
			log.info("目前的验证码是：" + validateCode);

			SMSUtils.sendMessage("外卖", "", userPhone, validateCode);
			// 短信签名
			// 短信的模板代码
			// 用户手机号，随机出来的验证码
			httpSession.setAttribute(userPhone, validateCode);
			return ReturnResult.sendSuccess("发送成功");
		}
		return ReturnResult.sendError("发送失败");
	}

	@PostMapping("/login")
	public ReturnResult<User> login(@RequestBody Map map, HttpSession httpSession) {
		String phone = map.get("phone").toString();
		String code = map.get("code").toString();
		Object attribute = httpSession.getAttribute(phone);
		if (attribute != null && attribute.equals(code)) {
			LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
			lambdaQueryWrapper.eq(User::getPhone, phone);
			User one = userService.getOne(lambdaQueryWrapper);// 需要完成判空操作
			if (one == null) {
				one = new User();
				one.setPhone(phone);
				one.setStatus(1);
				userService.save(one);
			}
			return ReturnResult.sendSuccess(one);
		}
		return ReturnResult.sendError("发送失败");
	}
}
