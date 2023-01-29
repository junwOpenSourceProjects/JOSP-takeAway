package junw.common;

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
public class ReturnResult<T> {
	private Integer code;
	// 这里前端和后端拿到的变量必须一致，否则直接res.code会无法跳转

	private String msg;
	private T data;
	private Map map = new HashMap();

	public static <T> ReturnResult<T> sendSuccess(T object) {
		ReturnResult<T> tReturnResult = new ReturnResult<>();
		tReturnResult.setData(object);
		tReturnResult.setCode(1);
		return tReturnResult;
	}

	public static <T> ReturnResult<T> sendError(String returnMsg) {
		ReturnResult<T> tReturnResult = new ReturnResult<>();
		tReturnResult.setCode(0);
		tReturnResult.setMsg(returnMsg);
		return tReturnResult;
	}

	public ReturnResult<T> sendAdd(String key, Object value) {
		this.map.put(key, value);
		return this;
	}
}
