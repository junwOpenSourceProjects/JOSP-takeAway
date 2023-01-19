package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.ChainQuery;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import junw.common.ReturnResult;
import junw.entry.Employee;
import junw.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-36  星期四
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	/**
	 * 用户登录
	 *
	 * @param httpServletRequest httpServletRequest
	 * @param employee           employee
	 * @return 登录结果
	 */
	@PostMapping("/login")
	public ReturnResult<Employee> userLogin(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
		// 我们登录成功以后，需要将用户信息保存到session中
		// 我们后期寻找用户的信息，直接httpServletRequest调用get即可
		String employeePassword = employee.getPassword();// 获取密码
		employeePassword = DigestUtils.md5DigestAsHex(employeePassword.getBytes());// 密码通过MD5加密，然后保存回去
		LambdaQueryWrapper<Employee> chainWrapper = new LambdaQueryWrapper<>();
		chainWrapper.eq(Employee::getUsername, employee.getUsername());// 这里是eq，不是select
		Employee serviceOne = employeeService.getOne(chainWrapper);

		if (serviceOne == null) {
			return ReturnResult.sendError("结果为空");
		}
		if (!serviceOne.getPassword().equals(employeePassword)) {
			return ReturnResult.sendError("密码错误");
		}
		if (serviceOne.getStatus() != 1) {
			return ReturnResult.sendError("员工账号停用");
		}
		log.info("登录成功");
		// httpServletRequest.getSession().setAttribute("employeeId", employee.getId());
		// 注意一下这里，要保存的是数据库查询得到的结果
		// 不是登录过程中，传递过来的登录信息
		httpServletRequest.getSession().setAttribute("employeeId", serviceOne.getId());

		// return employeeReturnResult.setReturnData(employee);
		// 不是直接使用set方式来保存对象
		// 而是直接调用静态方法，保存上面返回的数据
		return ReturnResult.sendSuccess(serviceOne);
	}
}
