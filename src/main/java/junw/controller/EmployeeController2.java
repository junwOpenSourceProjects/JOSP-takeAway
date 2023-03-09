package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import junw.common.ReturnResult;
import junw.entity.Employee;
import junw.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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
@RequestMapping("/employee2")
@Api(tags = "员工相关接口")
public class EmployeeController2 {
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
		String employeePassword = employee.getPassword();// 获取密码
		employeePassword = DigestUtils.md5DigestAsHex(employeePassword.getBytes());// 密码通过MD5加密，然后保存回去
		LambdaQueryWrapper<Employee> chainWrapper = new LambdaQueryWrapper<>();
		chainWrapper.eq(Employee::getUsername, employee.getUsername());// 这里是eq，不是select
		Employee serviceOne = employeeService.getOne(chainWrapper);

		if (serviceOne == null) {
			return ReturnResult.sendError("该用户不存在！");
		}
		if (!serviceOne.getPassword().equals(employeePassword)) {
			return ReturnResult.sendError("密码错误");
		}
		if (serviceOne.getStatus() != 1) {
			return ReturnResult.sendError("员工账号停用");
		}
		log.info("登录成功");
		httpServletRequest.getSession().setAttribute("employeeInfo", serviceOne.getId());// 保存信息

		return ReturnResult.sendSuccess(serviceOne);
	}

	/**
	 * 登出功能
	 *
	 * @param httpServletRequest httpServletRequest
	 * @return 返回是否退出成功
	 */
	@PostMapping("/logout")
	public ReturnResult<String> logout(HttpServletRequest httpServletRequest) {
		httpServletRequest.getSession().removeAttribute("employeeInfo");
		return ReturnResult.sendSuccess("退出成功");
	}

	/**
	 * 新增员工
	 *
	 * @param employee 实体类
	 * @return 返回结果
	 */
	@PostMapping
	public ReturnResult<String> saveEmployee(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
		log.info("新增员工：" + employee.toString());
		employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));// 需要加密
		// 这里统一设置初试登录密码为123456
		// =================================================================
		// 因为我们在实体类中已经添加了自动新增和自动插入的fill属性，同时在common中做了一个拦截器
		// 所以下面的四个属性全部都可以自动完成，这是mybatisPlus框架提供的服务
		// employee.setCreateTime(new Date());
		// employee.setUpdateTime(new Date());
		// employee.setCreateUser(id);
		// employee.setUpdateUser(id);
		// =================================================================
		log.info("当前的id为：" + httpServletRequest.getSession().getAttribute("employeeInfo"));
		Long id = (Long) httpServletRequest.getSession().getAttribute("employeeInfo");
		employeeService.save(employee);
		return ReturnResult.sendSuccess("新增一个用户");
	}

	/**
	 * 分页查询
	 *
	 * @param page     分页
	 * @param pageSize 分页数量
	 * @param name     姓名
	 * @return 分页结果
	 */
	@GetMapping("/page")
	public ReturnResult<Page> getPage(int page, int pageSize, String name) {
		// 这里的name，就是搜索框中输入的字符
		log.info("我是page的数据：{}，我是pageSize的数据：{}，我是姓名{}", page, pageSize, name);

		Page page1 = new Page(page, pageSize);
		LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();// lambda表达式
		if (name != null) {
			lambdaQueryWrapper.like(Employee::getName, name);
		}
		lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);// 按照更新时间排序

		employeeService.page(page1, lambdaQueryWrapper);// 分页查询
		return ReturnResult.sendSuccess(page1);
	}

	/**
	 * 更新用户信息
	 *
	 * @param employee 员工
	 * @return 是否修改成功
	 */
	@PutMapping()
	public ReturnResult<String> updateAccount(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
		employee.setUpdateUser((Long) httpServletRequest.getSession().getAttribute("employeeInfo"));// 获取登录信息
		employee.setUpdateTime(new Date());// 设置更新时间
		employeeService.updateById(employee);// 根据id主键更新
		return ReturnResult.sendSuccess("修改成功");
	}

	/**
	 * 根据id查询原始数据
	 *
	 * @param id id
	 * @return 返回结果
	 */
	@GetMapping("/{id}")
	public ReturnResult<Employee> getById(@PathVariable long id) {
		log.info("根据id获取参数");
		Employee byId = employeeService.getById(id);

		if (byId != null) {
			return ReturnResult.sendSuccess(byId);// 查询成功
		}
		return ReturnResult.sendError("无结果");
	}
}
