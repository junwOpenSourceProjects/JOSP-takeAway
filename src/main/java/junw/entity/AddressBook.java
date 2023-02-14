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
public class AddressBook implements Serializable {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("用户id")
	private Long userId;

	@ApiModelProperty("收货人")
	private String consignee;

	@ApiModelProperty("性别 0 女 1 男")
	private Byte sex;

	@ApiModelProperty("手机号")
	private String phone;

	@ApiModelProperty("省级区划编号")
	private String provinceCode;

	@ApiModelProperty("省级名称")
	private String provinceName;

	@ApiModelProperty("市级区划编号")
	private String cityCode;

	@ApiModelProperty("市级名称")
	private String cityName;

	@ApiModelProperty("区级区划编号")
	private String districtCode;

	@ApiModelProperty("区级名称")
	private String districtName;

	@ApiModelProperty("详细地址")
	private String detail;

	@ApiModelProperty("标签")
	private String label;

	@ApiModelProperty("默认 0 否 1是")
	private Byte isDefault;

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
