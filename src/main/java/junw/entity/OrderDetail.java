package junw.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author junw
 */
@Data
public class OrderDetail implements Serializable {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("名字")
	private String name;

	@ApiModelProperty("图片")
	private String image;

	@ApiModelProperty("订单id")
	private Long orderId;

	@ApiModelProperty("菜品id")
	private Long dishId;

	@ApiModelProperty("套餐id")
	private Long setmealId;

	@ApiModelProperty("口味")
	private String dishFlavor;

	@ApiModelProperty("数量")
	private Integer number;

	@ApiModelProperty("金额")
	private BigDecimal amount;

	private static final long serialVersionUID = 1L;
}
