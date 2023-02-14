package junw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author junw
 */
@Data
public class Orders implements Serializable {
	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("订单号")
	private String number;

	@ApiModelProperty("订单状态 1待付款，2待派送，3已派送，4已完成，5已取消")
	private Integer status;

	@ApiModelProperty("下单用户")
	private Long userId;

	@ApiModelProperty("地址id")
	private Long addressBookId;

	@ApiModelProperty("下单时间")
	private Date orderTime;

	/**
	 *
	 */
	@ApiModelProperty("结账时间")
	private Date checkoutTime;

	/**
	 *
	 */
	@ApiModelProperty("支付方式 1微信,2支付宝")
	private Integer payMethod;

	/**
	 *
	 */
	@ApiModelProperty("实收金额")
	private BigDecimal amount;

	/**
	 *
	 */
	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("")
	private String phone;

	@ApiModelProperty("")
	private String address;

	@ApiModelProperty("")
	private String userName;

	@ApiModelProperty("")
	private String consignee;

	private static final long serialVersionUID = 1L;
}
