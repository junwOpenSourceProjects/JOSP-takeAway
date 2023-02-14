package junw.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author junw
 */
@Data
public class SetmealDish implements Serializable {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("套餐id")
	private String setmealId;

	@ApiModelProperty("菜品id")
	private String dishId;

	/**
	 * 菜品名称 （冗余字段）
	 * 根据id就可以直接查询出来name
	 */
	@ApiModelProperty("菜品名称 （冗余字段）")
	private String name;

	@ApiModelProperty("菜品原价（冗余字段）")
	private BigDecimal price;

	@ApiModelProperty("份数")
	private Integer copies;

	/**
	 *
	 */
	@ApiModelProperty("排序")
	private Integer sort;

	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private Date createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private Date updateTime;

	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人")
	private Long createUser;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("修改人")
	private Long updateUser;

	@ApiModelProperty("是否删除")
	private Integer isDeleted;

	private static final long serialVersionUID = 1L;
}
