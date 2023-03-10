package junw.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author junw
 */
@Data
public class QueryDemo implements Serializable {

	@ApiModelProperty("主键")
	private Long id;

	@ApiModelProperty("姓名")
	private String timestamp;

	@ApiModelProperty("手机号")
	private String author;

	@ApiModelProperty("性别")
	private String reviewer;

	@ApiModelProperty("身份证号")
	private String title;

	@ApiModelProperty("头像")
	private String content_short;

	@ApiModelProperty("状态")
	private String content;
	@ApiModelProperty("状态")
	private String forecast;
	@ApiModelProperty("状态")
	private String importance;
	@ApiModelProperty("状态")
	private String type;
	@ApiModelProperty("状态")
	private String status;
	@ApiModelProperty("状态")
	private String display_time;
	@ApiModelProperty("状态")
	private String comment_disabled;
	@ApiModelProperty("状态")
	private String pageviews;
	@ApiModelProperty("状态")
	private String image_uri;
	@ApiModelProperty("状态")
	private String platforms;
	private static final long serialVersionUID = 1L;
}
