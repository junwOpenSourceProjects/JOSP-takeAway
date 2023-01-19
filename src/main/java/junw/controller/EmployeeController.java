package junw.controller;

import junw.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-36  星期四
 * @description
 */
@Controller
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

}
