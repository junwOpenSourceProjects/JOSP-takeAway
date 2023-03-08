package junw.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 员工信息(Employee)实体类
 *
 * @author junw
 * @since 2023-01-19 14:22:53
 */
@Data
public class Employee2 implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableField(select = false)
	@TableId
	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("姓名")
	private String name;

	@ApiModelProperty("用户名")
	private String username;

	@ApiModelProperty("密码")
	private String password;

	@ApiModelProperty("手机号")
	private String phone;

	@ApiModelProperty("性别")
	private String sex;

	@ApiModelProperty("身份证号")
	private String idNumber;

	@ApiModelProperty("状态 0:禁用，1:正常")
	private Integer status;
	/**
	 * 创建时间
	 * 插入和更新的同时，自动填充该字段
	 */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private Date createTime;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private Date updateTime;
	/**
	 * 创建人
	 * fill = FieldFill.INSERT
	 * 插入的时候自动填充字段
	 */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人")
	private Long createUser;

	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("修改人")
	private Long updateUser;
}
