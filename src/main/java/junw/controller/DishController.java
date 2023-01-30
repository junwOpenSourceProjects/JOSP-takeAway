package junw.controller;

import junw.common.ReturnResult;
import junw.dto.DishDto;
import junw.service.DishFlavorService;
import junw.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-01-19-02  星期五
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
	@Autowired
	private DishService dishService;

	@Autowired
	private DishFlavorService dishFlavorService;

	@PostMapping
	public ReturnResult<String> saveOne(@RequestBody DishDto dishDto) {
		log.info("我是save方法中提交的数据：" + dishDto);
		dishService.saveDishWithFlavor(dishDto);


		return ReturnResult.sendSuccess("插入成功");
	}


}
