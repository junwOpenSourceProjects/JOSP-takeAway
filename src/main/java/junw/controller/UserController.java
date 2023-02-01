package junw.controller;

import junw.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
