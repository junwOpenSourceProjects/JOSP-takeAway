package junw.controller;

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
@RequestMapping("/order")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;

	@PostMapping("/submit")
	public ReturnResult<String> submitOneOrder(@RequestBody Orders orders) {
		ordersService.submitOneOrder(orders);

		return ReturnResult.sendSuccess("下单成功");
	}


}
