package junw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author junw
 */
@Data
public class ShoppingCart implements Serializable {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 图片
	 */
	private String image;

	/**
	 * 主键
	 */
	private Long userId;

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

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	private static final long serialVersionUID = 1L;
}
