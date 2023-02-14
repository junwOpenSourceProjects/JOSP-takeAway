package junw.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author junw
 */
@Data
public class DishFlavor implements Serializable {

	@ApiModelProperty("主键")
	private Long id;


	@ApiModelProperty("菜品")
	private Long dishId;


	@ApiModelProperty("口味名称")
	private String name;


	@ApiModelProperty("口味数据list")
	private String value;


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
