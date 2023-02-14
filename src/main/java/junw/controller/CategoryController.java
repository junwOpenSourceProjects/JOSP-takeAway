package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.entity.Category;
import junw.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
 * @Date 2023-01-15-48  星期五
 * @description
 */
@RestController
@Slf4j
@Api(tags = "套餐相关接口")
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	/**
	 * 我是添加菜单和套餐
	 *
	 * @param category 实体类
	 * @return 成功失败
	 */
	@PostMapping
	@ApiOperation("我是添加菜单和套餐")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "category", value = "category实体类", required = true)
	})
	public ReturnResult<String> saveOneCategory(@RequestBody Category category) {
		log.info("我是添加方法");

		if (category != null) {
			categoryService.save(category);
			return ReturnResult.sendSuccess("添加成功");
		}
		return ReturnResult.sendError("添加失败");
	}

	/**
	 * 分页
	 *
	 * @param page     页面
	 * @param pageSize 页面条数
	 * @return 分页
	 */
	@GetMapping("/page")
	@ApiOperation("分页")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "页面", required = true),
			@ApiImplicitParam(name = "pageSize", value = "每页数据", required = true)
	})
	public ReturnResult<Page> page(int page, int pageSize) {
		Page<Category> page1 = new Page<>(page, pageSize);
		LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.orderByDesc(Category::getSort);
		categoryService.page(page1, lambdaQueryWrapper);
		return ReturnResult.sendSuccess(page1);
	}

	/**
	 * 根据id删除一条数据
	 *
	 * @param id id
	 * @return 删除结果
	 */
	@DeleteMapping
	@ApiOperation("根据id删除一条数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "实体主键", required = true)
	})
	public ReturnResult<String> delete(Long id) {
		log.info("我是删除方法，删除id为" + id);
		// categoryService.removeById(id);
		categoryService.removeCategory(id);
		return ReturnResult.sendSuccess("删除成功");
	}

	/**
	 * 根据id更新
	 *
	 * @param category 实体类
	 * @return 返回更新成功
	 */
	@PutMapping
	@ApiOperation("根据id更新")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "category", value = "category实体类", required = true)
	})
	public ReturnResult<String> updateById(Category category) {
		categoryService.updateById(category);
		return ReturnResult.sendSuccess("修改分类信息成功");
	}

	/**
	 * 根据条件查询分类信息
	 *
	 * @param category 实体类
	 * @return 类别list
	 */
	@GetMapping("/list")
	@ApiOperation("根据条件查询分类信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "category", value = "category实体类", required = true)
	})
	public ReturnResult<List<Category>> listCategory(Category category) {
		log.info("修改分类信息" + category);
		LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());
		lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
		List<Category> categoryList = categoryService.list(lambdaQueryWrapper);// 使用mybatis框架，直接返回一个list
		log.info("返回成功");
		return ReturnResult.sendSuccess(categoryList);
	}
}
