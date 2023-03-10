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
public class Dish implements Serializable {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("菜品名称")
	private String name;

	@ApiModelProperty("菜品分类id")
	private Long categoryId;

	@ApiModelProperty("菜品价格")
	private BigDecimal price;

	@ApiModelProperty("商品码")
	private String code;

	@ApiModelProperty("图片")
	private String image;

	@ApiModelProperty("描述信息")
	private String description;

	@ApiModelProperty("0 停售 1 起售")
	private Integer status;

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

	@ApiModelProperty("是否删除")
	private Integer isDeleted;

	private static final long serialVersionUID = 1L;
}
