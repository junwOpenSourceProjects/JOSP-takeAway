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
public class ReturnResult<T> {
	@ApiModelProperty("返回类型")
	private Integer code;
	// 这里前端和后端拿到的变量必须一致，否则直接res.code会无法跳转

	@ApiModelProperty("返回信息")
	private String msg;
	@ApiModelProperty("返回数据")
	private T data;
	private Map map = new HashMap();

	/**
	 * 请求成功
	 *
	 * @param object 对象
	 * @param <T>    成功消息/实体类
	 * @return 返回结果
	 */
	public static <T> ReturnResult<T> sendSuccess(T object) {
		ReturnResult<T> tReturnResult = new ReturnResult<>();
		tReturnResult.setData(object);
		tReturnResult.setCode(1);
		return tReturnResult;
	}

	/**
	 * 请求失败
	 *
	 * @param returnMsg 失败消息
	 * @param <T>       失败消息/实体类
	 * @return 返回结果
	 */
	public static <T> ReturnResult<T> sendError(String returnMsg) {
		ReturnResult<T> tReturnResult = new ReturnResult<>();
		tReturnResult.setCode(0);
		tReturnResult.setMsg(returnMsg);
		return tReturnResult;
	}

	/**
	 * 返回新增
	 *
	 * @param key   字符串
	 * @param value 对象
	 * @return 任意类型
	 */
	public ReturnResult<T> sendAdd(String key, Object value) {
		this.map.put(key, value);
		return this;
	}
}
