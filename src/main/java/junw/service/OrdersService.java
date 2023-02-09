package junw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import junw.entity.Orders;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service
 *
 * @author liujiajun_junw
 * @Date 2023-02-16-57  星期四
 * @description
 */
public interface OrdersService extends IService<Orders> {

	/**
	 * 用户下单
	 *
	 * @param orders 实体类
	 */
	public void submitOneOrder(Orders orders);
}
