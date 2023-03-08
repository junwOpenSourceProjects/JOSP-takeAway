package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.common.ThreadLocalBaseContent;
import junw.entity.ShoppingCart;
import junw.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @Date 2023-02-15-24  星期四
 * @description
 */
@Slf4j
@RestController
@Api(tags = "购物车相关接口")
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService shoppingCartService;


	/**
	 * 添加菜品到购物车
	 *
	 * @param shoppingCart 实体类
	 * @return 返回添加成功
	 */
	@PostMapping("/add")
	@ApiOperation("添加菜品到购物车")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "shoppingCart", value = "shoppingCart实体", required = true)
	})
	public ReturnResult<ShoppingCart> addOne(@RequestBody ShoppingCart shoppingCart) {
		log.info("添加一个到购物车");
		shoppingCart.setUserId(ThreadLocalBaseContent.getUserId());
		LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());

		Long dishId = shoppingCart.getDishId();
		if (dishId != null) {
			lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
		} else {
			lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
		}

		ShoppingCart scOne = shoppingCartService.getOne(lambdaQueryWrapper);
		if (scOne.getNumber() != null) {
			int newNum = scOne.getNumber() + 1;
			scOne.setNumber(newNum);
			shoppingCartService.updateById(scOne);// 直接将变化数据更新进去
		} else {
			// 如果是新的套餐，那么就直接保存一个即可
			shoppingCart.setNumber(1);
			shoppingCartService.save(shoppingCart);
			scOne = shoppingCart;
		}

		return ReturnResult.sendSuccess(scOne);
	}

	/**
	 * 展示购物车数据
	 * @return 购物清单
	 */
	@GetMapping("/list")
	@ApiOperation("展示购物车数据")
	public ReturnResult<List<ShoppingCart>> showMeShoppingCart() {
		LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ShoppingCart::getUserId, ThreadLocalBaseContent.getUserId());
		lambdaQueryWrapper.orderByDesc(ShoppingCart::getCreateTime);
		List<ShoppingCart> shoppingCartList = shoppingCartService.list(lambdaQueryWrapper);

		return ReturnResult.sendSuccess(shoppingCartList);
	}

	/**
	 * 清空购物车接口
	 * @param shoppingCart 购物车
	 * @return 购物车
	 */
	@DeleteMapping
	@ApiOperation("清空购物车接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "shoppingCart", value = "shoppingCart实体", required = true)
	})
	public ReturnResult<String> cleanShoppingCart(@RequestBody ShoppingCart shoppingCart) {
		LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId());
		shoppingCartService.remove(lambdaQueryWrapper);
		return ReturnResult.sendSuccess("清空成功");
	}
}
