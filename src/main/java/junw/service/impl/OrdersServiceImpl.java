package junw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.common.CustomException;
import junw.common.ThreadLocalBaseContent;
import junw.entity.AddressBook;
import junw.entity.OrderDetail;
import junw.entity.Orders;
import junw.entity.ShoppingCart;
import junw.entity.User;
import junw.mapper.OrdersMapper;
import junw.service.AddressBookService;
import junw.service.OrderDetailService;
import junw.service.OrdersService;
import junw.service.ShoppingCartService;
import junw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service.impl
 *
 * @author liujiajun_junw
 * @Date 2023-02-16-57  星期四
 * @description
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@Autowired
	private OrdersService ordersService;

	@Autowired
	private UserService userService;

	@Autowired
	private AddressBookService addressBookService;

	@Autowired
	private OrderDetailService orderDetailService;


	/**
	 * 用户下单
	 *
	 * @param orders 实体类
	 */
	@Override
	@Transactional
	public void submitOneOrder(Orders orders) {

		Long userId = ThreadLocalBaseContent.getUserId();
		LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ShoppingCart::getUserId, userId);
		List<ShoppingCart> shoppingCartList = shoppingCartService.list(lambdaQueryWrapper);
		if (shoppingCartList == null || shoppingCartList.size() == 0) {
			throw new CustomException("购物车为空");
		}
		Long addressBookId = orders.getAddressBookId();// 地址
		AddressBook addressBookById = addressBookService.getById(addressBookId);

		if (addressBookById == null) {
			throw new CustomException("地址为空");
		}
		long randomOrderId = IdWorker.getId();// 随机一个订单号出来

		AtomicInteger amountTotal = new AtomicInteger(0);// 设置总金额，原子操作，保证多线程的情况下没有问题

		List<OrderDetail> orderDetailList = shoppingCartList.stream().map((item) -> {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrderId(randomOrderId);
			orderDetail.setNumber(item.getNumber());
			orderDetail.setDishFlavor(item.getDishFlavor());
			orderDetail.setDishId(item.getDishId());
			orderDetail.setSetmealId(item.getSetmealId());
			orderDetail.setName(item.getName());
			orderDetail.setImage(item.getImage());
			orderDetail.setAmount(item.getAmount());
			amountTotal.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());

			return orderDetail;
		}).collect(Collectors.toList());


		User userById = userService.getById(userId);// 用户信息
		// 插入订单之前，需要补充对应的字段
		orders.setNumber(String.valueOf(randomOrderId));
		orders.setId(randomOrderId);
		orders.setOrderTime(new Date());
		orders.setCheckoutTime(new Date());
		orders.setStatus(2);
		orders.setAmount(new BigDecimal(2));// 从前端拿数据
		orders.setUserId(userId);
		orders.setNumber(String.valueOf(randomOrderId));
		orders.setUserName(userById.getName());
		orders.setConsignee(addressBookById.getConsignee());
		orders.setPhone(addressBookById.getPhone());
		orders.setAddress(
				addressBookById.getProvinceName()
						+ addressBookById.getCityName()
						+ addressBookById.getDistrictName()
						+ addressBookById.getDetail());


		this.save(orders);

		orderDetailService.saveBatch(orderDetailList);

		shoppingCartService.remove(lambdaQueryWrapper);// 删除购物车

	}
}
