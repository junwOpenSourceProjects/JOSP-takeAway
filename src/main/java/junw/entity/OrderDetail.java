package junw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author junw
 */
@Data
public class OrderDetail implements Serializable {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 名字
	 */
	private String name;

	/**
	 * 图片
	 */
	private String image;

	/**
	 * 订单id
	 */
	private Long orderId;

	/**
	 * 菜品id
	 */
	private Long dishId;

	/**
	 * 套餐id
	 */
	private Long setmealId;

	/**
	 * 口味
	 */
	private String dishFlavor;

	/**
	 * 数量
	 */
	private Integer number;

	/**
	 * 金额
	 */
	private BigDecimal amount;

	private static final long serialVersionUID = 1L;
}
