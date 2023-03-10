package junw.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.common
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-42  星期四
 * @description 通用返回结果
 */
@Data
@ApiModel("返回结果")
public class ReturnResult2<T> {
	@ApiModelProperty("返回类型")
	private Integer code;
	// 这里前端和后端拿到的变量必须一致，否则直接res.code会无法跳转

	@ApiModelProperty("返回数据")
	private T data;


	/**
	 * 请求成功
	 *
	 * @param object 对象
	 * @param <T>    成功消息/实体类
	 * @return 返回结果
	 */
	public static <T> ReturnResult2<T> sendSuccess(T object) {
		ReturnResult2<T> tReturnResult = new ReturnResult2<>();
		tReturnResult.setData(object);
		tReturnResult.setCode(20000);
		return tReturnResult;
	}

}
