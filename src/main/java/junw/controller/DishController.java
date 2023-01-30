package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import junw.common.ReturnResult;
import junw.dto.DishDto;
import junw.entity.Dish;
import junw.service.DishFlavorService;
import junw.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping("/page")
	public ReturnResult<Page> page(int page, int pageSize, String name) {
		Page<Dish> dishPage = new Page<>(page, pageSize);
		// dishPage.getTotal()
		LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		// lambdaQueryWrapper.eq(Dish::getName, name);
		lambdaQueryWrapper.like(name != null, Dish::getName, name);
		lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
		dishService.page(dishPage,lambdaQueryWrapper);

		return ReturnResult.sendSuccess(dishPage);
	}

}
