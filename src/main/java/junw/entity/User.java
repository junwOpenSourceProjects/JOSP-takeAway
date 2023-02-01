package junw.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class User implements Serializable {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 姓名
	 */
	private String name;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 身份证号
	 */
	private String idNumber;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 状态 0:禁用，1:正常
	 */
	private Integer status;

	private static final long serialVersionUID = 1L;
}
