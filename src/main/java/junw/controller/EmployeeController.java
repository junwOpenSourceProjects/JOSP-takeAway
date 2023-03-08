package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.entity.Employee;
import junw.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
@Api(tags = "员工相关接口")
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
	@ApiOperation("登录接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "httpServletRequest", value = "拿信息的"),
			@ApiImplicitParam(name = "employee", value = "employee实体", required = true)
	})
	public ReturnResult<Employee> userLogin(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
		// 我们登录成功以后，需要将用户信息保存到session中
		// 我们后期寻找用户的信息，直接httpServletRequest调用get即可
		String employeePassword = employee.getPassword();// 获取密码
		employeePassword = DigestUtils.md5DigestAsHex(employeePassword.getBytes());// 密码通过MD5加密，然后保存回去
		LambdaQueryWrapper<Employee> chainWrapper = new LambdaQueryWrapper<>();
		chainWrapper.eq(Employee::getUsername, employee.getUsername());// 这里是eq，不是select
		Employee serviceOne = employeeService.getOne(chainWrapper);
		// getone的前提是我们的数据库，已经对其做好了唯一约束

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
		// httpServletRequest.getSession().setAttribute("employeeId", employee.getId());
		// 注意一下这里，要保存的是数据库查询得到的结果
		// 不是登录过程中，传递过来的登录信息
		// 之前我们保存到mysql中的是加密后的密码e10adc3949ba59abbe56e057f20f883e
		// 所以这里是通过"9d022c5b-c604-40ec-80a7-1bb51d502543"
		httpServletRequest.getSession().setAttribute("employeeInfo", serviceOne.getId());

		// return employeeReturnResult.setReturnData(employee);
		// 不是直接使用set方式来保存对象
		// 而是直接调用静态方法，保存上面返回的数据
		return ReturnResult.sendSuccess(serviceOne);
	}

	/**
	 * 登出功能
	 *
	 * @param httpServletRequest httpServletRequest
	 * @return 返回是否退出成功
	 */
	@PostMapping("/logout")
	@ApiOperation("登出接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "httpServletRequest", value = "拿信息的", required = true)
	})
	public ReturnResult<String> logout(HttpServletRequest httpServletRequest) {
		// 我们前面登录的时候，使用了session
		// 这里直接去session中拿到用户数据就可以校验和设置退出功能
		httpServletRequest.getSession().removeAttribute("employeeInfo");
		// 直接拿到之前添加的session就可以完成移除操作
		return ReturnResult.sendSuccess("退出成功");
	}

	/**
	 * 新增员工
	 *
	 * @param employee 实体类
	 * @return 返回结果
	 */
	@PostMapping
	@ApiOperation("新增员工")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "httpServletRequest", value = "拿信息的"),
			@ApiImplicitParam(name = "employee", value = "employee实体", required = true)
	})
	public ReturnResult<String> saveEmployee(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
		// 因为前端的数据是json形式的，所以这里必须添加RequestBody才能正常封装
		log.info("新增员工：" + employee.toString());
		// employee.setPassword("123456");// 设置员工的初试密码
		employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));// 需要加密
		// employee.setCreateTime(Date.from(LocalTime.now()));
		employee.setCreateTime(new Date());
		employee.setUpdateTime(new Date());
		// 获取创建和更新时间
		// log.info("当前的id为：" + httpServletRequest.getSession().getId());
		log.info("当前的id为：" + httpServletRequest.getSession().getAttribute("employeeInfo"));
		Long id = (Long) httpServletRequest.getSession().getAttribute("employeeInfo");
		// 这里直接从getAttribute中，就可以直接拿到里面的id和所有信息
		employee.setCreateUser(id);
		// 111111111111111111
		// 18912341234
		// 获取具体的id
		employee.setUpdateUser(id);
		// 因为需要存到数据库中，所以需要调用一下service
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
	@ApiOperation("分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页面", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数据", required = true),
			@ApiImplicitParam(name = "name", value = "名称")
	})
	public ReturnResult<Page> getPage(int page, int pageSize, String name) {
		log.info("我是page的数据：{}，我是pageSize的数据：{}，我是姓名{}", page, pageSize, name);

		Page page1 = new Page(page, pageSize);
		LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
		// x.eq(Employee::getName, name)
		if (name != null) {
			lambdaQueryWrapper.like(Employee::getName, name);
		}
		lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

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
	@ApiOperation("更新用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "httpServletRequest", value = "拿信息的"),
			@ApiImplicitParam(name = "employee", value = "employee实体", required = true)
	})
	public ReturnResult<String> updateAccount(HttpServletRequest httpServletRequest, @RequestBody Employee employee) {
		employee.setUpdateUser((Long) httpServletRequest.getSession().getAttribute("employeeInfo"));
		employee.setUpdateTime(new Date());
		employeeService.updateById(employee);
		return ReturnResult.sendSuccess("修改成功");
	}

	/**
	 * 根据id查询原始数据
	 *
	 * @param id id
	 * @return 返回结果
	 */
	@GetMapping("/{id}")
	@ApiOperation("根据id查询一条数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "实体主键", required = true)
	})
	public ReturnResult<Employee> getById(@PathVariable long id) {
		log.info("根据id获取参数");
		Employee byId = employeeService.getById(id);

		if (byId != null) {
			return ReturnResult.sendSuccess(byId);
		}
		return ReturnResult.sendError("无结果");
	}
}
