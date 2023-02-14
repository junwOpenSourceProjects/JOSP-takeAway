package junw.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author junw
 */
@Data
@ApiModel("套餐信息")
public class Setmeal2 implements Serializable {
	// 这里是对上面的实体类进行了swagger改造

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("菜品分类id")
	private Long categoryId;

	@ApiModelProperty("套餐名称")
	private String name;

	@ApiModelProperty("套餐价格")
	private BigDecimal price;

	@ApiModelProperty("状态 0:停用 1:启用")
	private Integer status;

	@ApiModelProperty("编码")
	private String code;

	@ApiModelProperty("描述信息")
	private String description;

	@ApiModelProperty("图片")
	private String image;

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
