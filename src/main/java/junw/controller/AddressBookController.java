package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.common.ThreadLocalBaseContent;
import junw.entity.AddressBook;
import junw.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-02-20-47  星期四
 * @description
 */
@RestController
@Slf4j
@Api(tags = "地址相关接口")
@RequestMapping("/addressBook")
public class AddressBookController {
	@Autowired
	private DataSource dataSource;
	// 必须要注入一个数据源对象，否则读写分离无效

	@Autowired
	private AddressBookService addressBookService;

	/**
	 * 保存地址
	 *
	 * @param addressBook 实体类
	 * @return 地址
	 */
	@PostMapping
	@ApiOperation("新增套餐接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addressBook", value = "地址实体类", required = true)
	})
	public ReturnResult<AddressBook> saveOne(@RequestBody AddressBook addressBook) {
		addressBook.setUserId(ThreadLocalBaseContent.getUserId());// 获取用户id

		log.info("我保存了一个用户地址");
		addressBookService.save(addressBook);

		return ReturnResult.sendSuccess(addressBook);
	}

	/**
	 * 设置默认地址
	 *
	 * @param addressBook 实体类
	 * @return 地址
	 */
	@PutMapping("/default")
	@ApiOperation("设置默认地址接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addressBook", value = "地址实体类", required = true)
	})
	public ReturnResult<AddressBook> setDefaultAddress(@RequestBody AddressBook addressBook) {
		// addressBookService
		LambdaUpdateWrapper<AddressBook> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
		// 注意这里是update，不是query
		lambdaUpdateWrapper.eq(AddressBook::getUserId, ThreadLocalBaseContent.getUserId());
		lambdaUpdateWrapper.set(AddressBook::getIsDefault, 0);
		addressBookService.update(lambdaUpdateWrapper);
		addressBook.setIsDefault((byte) 1);
		addressBookService.updateById(addressBook);

		return ReturnResult.sendSuccess(addressBook);
	}

	/**
	 * 根据id获取地址
	 *
	 * @param id id
	 * @return 地址
	 */
	@ApiOperation("查询套餐接口")
	@GetMapping("/{id}")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "数据主键", required = true)
	})
	public ReturnResult<AddressBook> getOne(@PathVariable long id) {
		AddressBook serviceById = addressBookService.getById(id);
		if (serviceById != null) {
			return ReturnResult.sendSuccess(serviceById);
		}
		return ReturnResult.sendError("查询结果为空");
	}

	/**
	 * 获取默认
	 *
	 * @return 地址
	 */
	@ApiOperation("获取默认接口")
	@GetMapping("default")
	public ReturnResult<AddressBook> getDefault() {
		LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(AddressBook::getUserId, ThreadLocalBaseContent.getUserId());
		lambdaQueryWrapper.eq(AddressBook::getIsDefault, 1);
		AddressBook bookServiceOne = addressBookService.getOne(lambdaQueryWrapper);
		if (bookServiceOne != null) {
			return ReturnResult.sendSuccess(bookServiceOne);
		}
		return ReturnResult.sendError("结果错误");
	}

	/**
	 * 获取一个地址集合
	 *
	 * @param addressBook 实体类
	 * @return 地址list
	 */
	@GetMapping("/list")
	@ApiOperation("获取一个地址集合")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "addressBook", value = "地址实体类", required = true)
	})
	public ReturnResult<List<AddressBook>> list(AddressBook addressBook) {
		addressBook.setUserId(ThreadLocalBaseContent.getUserId());
		log.info("我是用户信息");
		LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
		lambdaQueryWrapper.orderByDesc(AddressBook::getUpdateTime);
		return ReturnResult.sendSuccess(addressBookService.list(lambdaQueryWrapper));
	}


}
