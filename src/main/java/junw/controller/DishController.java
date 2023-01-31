package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import junw.common.ReturnResult;
import junw.dto.DishDto;
import junw.entity.Category;
import junw.entity.Dish;
import junw.service.CategoryService;
import junw.service.DishFlavorService;
import junw.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private CategoryService categoryService;

	/**
	 * 保存一条数据
	 *
	 * @param dishDto 菜品信息
	 * @return 返回结果
	 */
	@PostMapping
	public ReturnResult<String> saveOne(@RequestBody DishDto dishDto) {
		log.info("我是save方法中提交的数据：" + dishDto);
		dishService.saveDishWithFlavor(dishDto);
		return ReturnResult.sendSuccess("插入成功");
	}

	/**
	 * 查询菜品，口味，分类信息的分页
	 *
	 * @param page     分页
	 * @param pageSize 分页数量
	 * @param name     名称
	 * @return 返回分页数据
	 */
	@GetMapping("/page")
	public ReturnResult<Page> page(int page, int pageSize, String name) {
		Page<Dish> pageInfo = new Page<>(page, pageSize);
		LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		// lambdaQueryWrapper.eq(Dish::getName, name);
		// 使用like可以模糊查询
		lambdaQueryWrapper.like(name != null, Dish::getName, name);
		lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
		dishService.page(pageInfo, lambdaQueryWrapper);
		// 讲一下这里的原理：
		// 我们在上面首先保存了一个page类型的对象，但是不是所有的数据都在里面
		// 因此我们需要使用dishDto的形式去保存完整数据
		Page<DishDto> dishDtoPage = new Page<>();
		// 因为大部分的属性，在dish中已经存在，所以直接copy就可以
		BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
		// 因为我们需要的list，实际上是dishDtoList，所以先将其忽略，在下面完成处理以后，再复制进去

		List<Dish> dishList = pageInfo.getRecords();// records实际上就是我们dish的list
		// 使用stream流，对其中的对象完成加工

		List<DishDto> dishDtoList = dishList.stream().map((item) -> {
			DishDto dishDto = new DishDto();// 新增一个dishDto对象
			BeanUtils.copyProperties(item, dishDto);// 属性复制过去
			Long categoryId = item.getCategoryId();// 获取其中的id
			Category category = categoryService.getById(categoryId);// 根据id拿到一个分类对象
			// 防止数据异常，出现空指针
			if (category != null) {
				String categoryName = category.getName();// 获取名称
				dishDto.setCategoryName(categoryName);// 将名称赋值过去
			}
			return dishDto;// 完成对象的新增过程
		}).collect(Collectors.toList());// 将多个对象转化为list类型的结果

		dishDtoPage.setRecords(dishDtoList);// 因为前面已经忽略了，所以实际上page中的list是空的
		// 这里直接赋值进去，就完成了所有数据的封装

		// return ReturnResult.sendSuccess(pageInfo);
		return ReturnResult.sendSuccess(dishDtoPage);
	}

	/**
	 * 根据id查询数据，前端的编辑功能
	 *
	 * @param id id
	 * @return dishDto
	 */
	@GetMapping("/{id}")
	public ReturnResult<DishDto> changOne(@PathVariable Long id) {
		DishDto dishDtoById = dishService.getDishDtoById(id);
		// 我们将具体的方法封到service中，然后调用的时候，可以提高复用性
		return ReturnResult.sendSuccess(dishDtoById);
	}

	@PutMapping
	public ReturnResult<String> updateOne(@RequestBody DishDto dishDto) {
		// log.info("我是save方法中提交的数据：" + dishDto);
		dishService.updateDishInfo(dishDto);
		return ReturnResult.sendSuccess("更新成功");
	}

	@GetMapping("/list")
	public ReturnResult<List<Dish>> getDishList(Dish dish) {
		// 每次点击的时候，都会发送一次请求，我们根据发送过来的菜式
		// 进一步查询菜式底下的套餐都有哪些
		LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
		lambdaQueryWrapper.eq(Dish::getStatus,1);
		lambdaQueryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
		List<Dish> dishList = dishService.list(lambdaQueryWrapper);
		return ReturnResult.sendSuccess(dishList);
	}
}
