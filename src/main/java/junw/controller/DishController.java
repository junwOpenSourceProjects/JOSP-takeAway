package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.dto.DishDto;
import junw.entity.Category;
import junw.entity.Dish;
import junw.entity.DishFlavor;
import junw.service.CategoryService;
import junw.service.DishFlavorService;
import junw.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
@Api(tags = "dish相关接口")
@Slf4j
@RequestMapping("/dish")
public class DishController {
	@Autowired
	private DishService dishService;

	@Autowired
	private DishFlavorService dishFlavorService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 保存一条数据
	 *
	 * @param dishDto 菜品信息
	 * @return 返回结果
	 */
	@PostMapping
	@ApiOperation("保存一条数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dishDto", value = "dishDto实体类", required = true)
	})
	public ReturnResult<String> saveOne(@RequestBody DishDto dishDto) {
		log.info("我是save方法中提交的数据：" + dishDto);
		dishService.saveDishWithFlavor(dishDto);
		// 和下面的update方法一样
		// 直接清理掉缓存中的key
		// 其实可以优化一下，直接从dto中拿到对应的key，然后定向清除
		Set keys = redisTemplate.keys("dish_*");// 拿到所有dish开头的key
		redisTemplate.delete(keys);// 删除所有对应key
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
	@ApiOperation("分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页面", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数据", required = true),
			@ApiImplicitParam(name = "name", value = "名称", required = false)
	})
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
	@ApiOperation("根据id查询一条数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "实体主键", required = true)
	})
	public ReturnResult<DishDto> changOne(@PathVariable Long id) {
		DishDto dishDtoById = dishService.getDishDtoById(id);
		// 我们将具体的方法封到service中，然后调用的时候，可以提高复用性
		return ReturnResult.sendSuccess(dishDtoById);
	}

	/**
	 * 更新一条数据
	 *
	 * @param dishDto 实体类
	 * @return 更新结果
	 */
	@PutMapping
	@ApiOperation("根据id更新一条数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dishDto", value = "dishDto实体", required = true)
	})
	public ReturnResult<String> updateOne(@RequestBody DishDto dishDto) {
		// log.info("我是save方法中提交的数据：" + dishDto);
		dishService.updateDishInfo(dishDto);

		Set keys = redisTemplate.keys("dish_*");// 拿到所有dish开头的key
		redisTemplate.delete(keys);// 删除所有对应key
		return ReturnResult.sendSuccess("更新成功");
	}

	/**
	 * 查询dish列表
	 *
	 * @param dish 实体类
	 * @return 实体类list
	 */
	@GetMapping("/list")
	@ApiOperation("查询dish列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dish", value = "dish实体", required = true)
	})
	public ReturnResult<List<Dish>> getDishList(Dish dish) {
		// 每次点击的时候，都会发送一次请求，我们根据发送过来的菜式
		// 进一步查询菜式底下的套餐都有哪些
		LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
		lambdaQueryWrapper.eq(Dish::getStatus, 1);
		lambdaQueryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
		List<Dish> dishList = dishService.list(lambdaQueryWrapper);
		return ReturnResult.sendSuccess(dishList);
	}

	/**
	 * 提取dish列表
	 *
	 * @param dish 实体
	 * @return dishDto的list
	 */
	@GetMapping("/list2")
	@ApiOperation("提取dish列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dish", value = "dish实体", required = true)
	})
	public ReturnResult<List<DishDto>> getDishDtoList(Dish dish) {
		LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
		lambdaQueryWrapper.eq(Dish::getStatus, 1);
		lambdaQueryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
		List<Dish> dishList = dishService.list(lambdaQueryWrapper);
		List<DishDto> dishDtoList = dishList.stream().map((item) -> {
			DishDto dishDto = new DishDto();
			BeanUtils.copyProperties(item, dishDto);
			Long categoryId = item.getCategoryId();
			Category byId = categoryService.getById(categoryId);
			if (byId != null) {
				String byIdName = byId.getName();
				dishDto.setName(byIdName);
			}
			Long dishId = item.getId();
			LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
			lambdaQueryWrapper1.eq(DishFlavor::getId, dishId);
			List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper1);
			dishDto.setFlavors(dishFlavors);
			return dishDto;
		}).collect(Collectors.toList());
		return ReturnResult.sendSuccess(dishDtoList);
	}


	/**
	 * 提取dish列表，加入redis缓存
	 *
	 * @param dish 实体
	 * @return dishDto的list
	 */
	@GetMapping("/list3")
	@ApiOperation("提取dish列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "dish", value = "dish实体", required = true)
	})
	public ReturnResult<List<DishDto>> getDishDtoList2(Dish dish) {
		// 这里上下三个方法是重复的，我只是没有删除而已
		// 用来看自己的接口迭代过程

		// 需要说明一下：
		// 如果多个用户同时使用系统，查询次数提高，数据库压力会非常大
		// 这里就可以将我们查询到的list载入缓存中，避免重复查询

		String redis_keys = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
		List<DishDto> dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(redis_keys);
		// 这里是强行转换一下，
		// 将我们从缓存中拿到的数据放进去
		if (dishDtoList != null) {
			return ReturnResult.sendSuccess(dishDtoList);
			// 如果缓存中有数据，直接返回即可
		}
		// 缓存没有数据，就完成一遍查询

		LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
		lambdaQueryWrapper.eq(Dish::getStatus, 1);
		lambdaQueryWrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
		List<Dish> dishList = dishService.list(lambdaQueryWrapper);
		dishDtoList = dishList.stream().map((item) -> {
			DishDto dishDto = new DishDto();
			BeanUtils.copyProperties(item, dishDto);
			Long categoryId = item.getCategoryId();
			Category byId = categoryService.getById(categoryId);
			if (byId != null) {
				String byIdName = byId.getName();
				dishDto.setName(byIdName);
			}
			Long dishId = item.getId();
			LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
			lambdaQueryWrapper1.eq(DishFlavor::getId, dishId);
			List<DishFlavor> dishFlavors = dishFlavorService.list(lambdaQueryWrapper1);
			dishDto.setFlavors(dishFlavors);
			return dishDto;
		}).collect(Collectors.toList());
		redisTemplate.opsForValue().set(redis_keys, dishDtoList, 60, TimeUnit.MINUTES);
		// 如果没有对应的数据，就将缓存结果保存为key-value

		return ReturnResult.sendSuccess(dishDtoList);
	}
}
