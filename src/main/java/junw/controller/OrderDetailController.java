package junw.controller;

import io.swagger.annotations.Api;
import junw.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/OrderDetail")
@Api(tags = "订单详情相关接口")
public class OrderDetailController {

	@Autowired
	private OrderDetailService orderDetailService;




}
