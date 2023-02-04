package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
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
@RequestMapping("/addressBook")
public class AddressBookController {

	@Autowired
	private AddressBookService addressBookService;

	@PostMapping
	public ReturnResult<AddressBook> save(@RequestBody AddressBook addressBook) {
		addressBook.setUserId(ThreadLocalBaseContent.getUserId());// 获取用户id

		log.info("我保存了一个用户地址");
		addressBookService.save(addressBook);

		return ReturnResult.sendSuccess(addressBook);
	}

	@PutMapping("/default")
	public ReturnResult<AddressBook> setDefualtAddress(@RequestBody AddressBook addressBook) {
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

	@GetMapping("/{id}")
	public ReturnResult<AddressBook> getOne(@PathVariable long id) {
		AddressBook serviceById = addressBookService.getById(id);
		if (serviceById != null) {
			return ReturnResult.sendSuccess(serviceById);
		}
		return ReturnResult.sendError("查询结果为空");
	}

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

	@GetMapping("/list")
	public ReturnResult<List<AddressBook>> list(AddressBook addressBook) {
		addressBook.setUserId(ThreadLocalBaseContent.getUserId());
		log.info("我是用户信息");
		LambdaQueryWrapper<AddressBook> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(addressBook.getUserId() != null, AddressBook::getUserId, addressBook.getUserId());
		lambdaQueryWrapper.orderByDesc(AddressBook::getUpdateTime);
		return ReturnResult.sendSuccess(addressBookService.list(lambdaQueryWrapper));
	}


}
