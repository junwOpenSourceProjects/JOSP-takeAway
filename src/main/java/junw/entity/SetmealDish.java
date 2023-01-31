package junw.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author junw
 */
@Data
public class SetmealDish implements Serializable {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 套餐id
	 */
	private String setmealId;

	/**
	 * 菜品id
	 */
	private String dishId;

	/**
	 * 菜品名称 （冗余字段）
	 * 根据id就可以直接查询出来name
	 */
	private String name;

	/**
	 * 菜品原价（冗余字段）
	 */
	private BigDecimal price;

	/**
	 * 份数
	 */
	private Integer copies;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	/**
	 * 创建人
	 */
	@TableField(fill = FieldFill.INSERT)
	private Long createUser;

	/**
	 * 修改人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Long updateUser;

	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	private static final long serialVersionUID = 1L;
}
