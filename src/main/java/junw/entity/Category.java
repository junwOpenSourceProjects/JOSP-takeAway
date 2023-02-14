package junw.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author junw
 */
@Data
public class Category implements Serializable {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("类型   1 菜品分类 2 套餐分类")
	private Integer type;

	@ApiModelProperty("分类名称")
	private String name;

	@ApiModelProperty("顺序")
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

	private static final long serialVersionUID = 1L;
}
