package junw.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.entity.AddressBook;
import junw.entity.Category;
import junw.service.AddressBookService;
import junw.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-02-16-52  星期日
 * @description
 */
@Slf4j
@RestController
@Api(tags = "缓存相关接口")
@RequestMapping("/cache")
public class CacheController {
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AddressBookService addressBookService;

	/**
	 * 测试缓存的方法
	 * <p>
	 * CachePut表示将方法的返回值放进去
	 * <p>
	 * value表示是一类缓存，最后会将ReturnResult.sendSuccess("插入成功");这个信息直接存进去
	 * <p>
	 * value下面可以有多个key同时存在
	 * <p>
	 * key表示缓存的名称
	 *
	 * @param category 实体类
	 * @return
	 */
	@PostMapping
	@CachePut(value = "categoryCache", key = "#category.id")
	@ApiOperation("保存一个套餐")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "category", value = "套餐实体类", required = true)
	})
	public ReturnResult<String> saveOneCategory(@RequestBody Category category) {
		if (category != null) {
			categoryService.save(category);
			return ReturnResult.sendSuccess("添加成功");
		}
		return ReturnResult.sendError("添加失败");
	}

	/**
	 * 根据id删除一条数据
	 * <p>
	 * 删除指定value的缓存
	 * <p>
	 * CacheEvict清理指定缓存
	 * <p>
	 * // @CacheEvict(value = "categoryCache", key = "#p0")
	 * <p>
	 * // @CacheEvict(value = "categoryCache", key = "#root.args[0]")
	 * <p>
	 * 这几种写法都是一样的效果
	 *
	 * @param id id
	 * @return 删除结果
	 */
	@DeleteMapping
	@CacheEvict(value = "categoryCache", key = "#id")
	@ApiOperation("根据id删除一条数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "对象主键", required = true)
	})
	public ReturnResult<String> delete(Long id) {
		categoryService.removeCategory(id);
		return ReturnResult.sendSuccess("删除成功");
	}


	/**
	 * 根据id更新
	 * <p>
	 * // @CacheEvict(value = "categoryCache", key = "#result.id")
	 * <p>
	 * 如果返回的结果是一个实体类，那么直接从result中取id也是可以的
	 *
	 * @param category 实体类
	 * @return 返回更新成功
	 */
	@CacheEvict(value = "categoryCache", key = "#p0.id",allEntries = true)
	@PutMapping
	@ApiOperation("根据id更新数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "category", value = "category实体类", required = true)
	})
	public ReturnResult<String> updateById(Category category) {
		categoryService.updateById(category);
		return ReturnResult.sendSuccess("修改分类信息成功");
	}

	/**
	 * 根据id获取地址
	 * <p>
	 * Cacheable,缓存的查询方法
	 * <p>
	 * // @Cacheable(value = "addressCache", key = "#id", condition = "#result!=null")
	 * <p>
	 * unless如果结果不为空，就缓存。
	 * <p>
	 * result拿不到上下文对象，所以要使用unless。
	 *
	 * @param id id
	 * @return 地址
	 */

	@Cacheable(value = "addressCache", key = "#id", unless = "#result==null")
	@GetMapping("/{id}")
	@ApiOperation("获取一个对象")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "实体主键", required = true)
	})
	public ReturnResult<AddressBook> getOne(@PathVariable long id) {
		AddressBook serviceById = addressBookService.getById(id);
		if (serviceById != null) {
			return ReturnResult.sendSuccess(serviceById);
		}
		return ReturnResult.sendError("查询结果为空");
	}

}
