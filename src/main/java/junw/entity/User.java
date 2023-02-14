package junw.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author junw
 */
@Data
public class User implements Serializable {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("姓名")
	private String name;

	@ApiModelProperty("手机号")
	private String phone;

	@ApiModelProperty("性别")
	private String sex;

	@ApiModelProperty("身份证号")
	private String idNumber;

	@ApiModelProperty("头像")
	private String avatar;

	@ApiModelProperty("状态 0:禁用，1:正常")
	private Integer status;

	private static final long serialVersionUID = 1L;
}
