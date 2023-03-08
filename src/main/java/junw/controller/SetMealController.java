package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "setmeal相关接口")
@RequestMapping("/setmeal")
public class SetMealController {
	@Autowired
	private SetMealService setMealService;

	@Autowired
	private SetmealDishService setmealDishService;

	@Autowired
	private CategoryService categoryService;

	/**
	 * 保存套餐接口
	 * @param setmealDto 实体类
	 * @return 提交结果
	 */
	@PostMapping
	@ApiOperation("保存套餐接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "setmealDto", value = "setmealDto实体类")
	})
	public ReturnResult<String> saveOne(@RequestBody SetmealDto setmealDto) {
		log.info("我是saveOne方法");
		setMealService.saveOneDish(setmealDto);

		return ReturnResult.sendSuccess("新增套餐");
	}

	/**
	 * 分页
	 * @param page 分页
	 * @param pageSize 每页数据
	 * @param name 查询条件
	 * @return 分页
	 */

	@GetMapping("/page")
	@ApiOperation("分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页面", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数据", required = true),
			@ApiImplicitParam(name = "name", value = "名称")
	})
	public ReturnResult<Page> page(int page, int pageSize, String name) {
		Page<Setmeal> setmealPage = new Page<>(page, pageSize);// 查询套餐
		Page<SetmealDto> dtoPage = new Page<>();// 查询页面展示出来的套餐dto

		LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();

		lambdaQueryWrapper.like(name != null, Setmeal::getName, name);// 名称可以为空
		lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime).orderByDesc(Setmeal::getStatus);//首先是更新时间，其次是展示状态
		setMealService.page(setmealPage, lambdaQueryWrapper);// 查询套餐分页

		BeanUtils.copyProperties(setmealPage, dtoPage, "records");// 拷贝属性过去
		List<Setmeal> setmealList = setmealPage.getRecords();// 获取分页中的套餐信息，然后将其保存为一个list，遍历
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
		}).collect(Collectors.toList());// 保存到新的setMealDto对应的list中
		dtoPage.setRecords(setmealDtos);// 将上面的list保存到page中

		return ReturnResult.sendSuccess(setmealPage);
	}

	/**
	 * 根据id删除套餐
	 *
	 * @param ids id
	 * @return 成功失败
	 */
	@DeleteMapping
	@ApiOperation("根据id删除套餐")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ids", value = "id的list", required = true),
	})
	public ReturnResult<String> delete(@RequestParam List<Long> ids) {
		// 这里的注解不是PathVariable,而是
		// 首先删除套餐中的数据
		// 然后是菜品数据，口味数据
		setMealService.removeWithDish(ids);

		return ReturnResult.sendSuccess("删除成功");
	}

	/**
	 * 获取所有setmeal
	 * @param setmeal 实体
	 * @return 获取setmeal的list
	 */
	@GetMapping("list")
	@ApiOperation("获取setmeal的list")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "setmeal", value = "setmeal实体类", required = true),
	})
	public ReturnResult<List<Setmeal>> getSetmealList(Setmeal setmeal) {
		// @RequestBody 不是随便加的
		// 如果参数是直接跟在请求后面，那么不需要
		LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
		lambdaQueryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
		lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

		List<Setmeal> setmealList = setMealService.list(lambdaQueryWrapper);
		return ReturnResult.sendSuccess(setmealList);
	}

}
