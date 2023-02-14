package junw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author junw
 */
@Data
public class ShoppingCart implements Serializable {
	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("图片")
	private String image;

	@ApiModelProperty("主键")
	private Long userId;

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

	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private Date createTime;

	private static final long serialVersionUID = 1L;
}
