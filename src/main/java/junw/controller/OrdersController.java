package junw.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult;
import junw.entity.Orders;
import junw.service.OrdersService;
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
 * @Date 2023-02-16-58  星期四
 * @description
 */
@Slf4j
@RestController
@Api(tags = "订单相关接口")
@RequestMapping("/order")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	/**
	 * 提交订单接口
	 * @param orders 实体类
	 * @return 提交结果
	 */
	@PostMapping("/submit")
	@ApiOperation("提交订单接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "orders", value = "orders实体类")
	})
	public ReturnResult<String> submitOneOrder(@RequestBody Orders orders) {
		ordersService.submitOneOrder(orders);

		return ReturnResult.sendSuccess("下单成功");
	}
}
