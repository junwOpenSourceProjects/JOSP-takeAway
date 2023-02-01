package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import junw.common.ReturnResult;
import junw.dto.SetmealDto;
import junw.entity.Category;
import junw.entity.Setmeal;
import junw.service.CategoryService;
import junw.service.SetMealService;
import junw.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-01-19-03  星期五
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetMealController {
	@Autowired
	private SetMealService setMealService;

	@Autowired
	private SetmealDishService setmealDishService;

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public ReturnResult<String> saveOne(@RequestBody SetmealDto setmealDto) {
		log.info("我是saveOne方法");
		setMealService.saveOneDish(setmealDto);

		return ReturnResult.sendSuccess("新增套餐");
	}

	@GetMapping("/page")
	public ReturnResult<Page> page(int page, int pageSize, String name) {
		Page<Setmeal> setmealPage = new Page<>(page, pageSize);
		Page<SetmealDto> dtoPage = new Page<>();

		LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();

		lambdaQueryWrapper.like(name != null, Setmeal::getName, name);
		lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime).orderByDesc(Setmeal::getStatus);
		setMealService.page(setmealPage, lambdaQueryWrapper);
		// dtoPage.se
		BeanUtils.copyProperties(setmealPage, dtoPage, "records");// 拷贝属性过去
		List<Setmeal> setmealList = setmealPage.getRecords();
		List<SetmealDto> setmealDtos = setmealList.stream().map((item) -> {
			SetmealDto setmealDto = new SetmealDto();
			Long categoryId = item.getCategoryId();// 根据id查询数据库
			Category byId = categoryService.getById(categoryId);
			if (byId != null) {
				String byIdName = byId.getName();
				setmealDto.setCategoryName(byIdName);// 设置名称对应的name
				BeanUtils.copyProperties(item, setmealDto);
			}
			// return item;
			return setmealDto;// 因为这里返回的是我们的setMealDto，
			// 所以这里就需要使用我们上面new出来的新list
		}).collect(Collectors.toList());
		dtoPage.setRecords(setmealDtos);

		return ReturnResult.sendSuccess(setmealPage);
	}

	/**
	 * 根据id删除套餐
	 *
	 * @param ids id
	 * @return 成功失败
	 */
	@DeleteMapping
	public ReturnResult<String> delete(@RequestParam List<Long> ids) {
		// 这里的注解不是PathVariable,而是
		// 首先删除套餐中的数据
		// 然后是菜品数据，口味数据
		setMealService.removeWithDish(ids);

		return ReturnResult.sendSuccess("删除成功");
	}

}
