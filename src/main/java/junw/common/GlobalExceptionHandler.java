package junw.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.common
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-15  星期四
 * @description
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
	// ControllerAdvice添加一个controller注解
	// annotations表示对哪些范围生效，有RestController注解的都可以生效
	// 因为需要返回json格式的数据，所以需要添加ResponseBody注解

	/**
	 * SQL异常拦截
	 *
	 * @param sqlException 异常体
	 * @return 出现主键重复，抛出到外面
	 */
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ReturnResult<String> exceptionHandler(SQLIntegrityConstraintViolationException sqlException) {
		log.error(sqlException.getMessage());// 获取SQL中的错误信息
		if (sqlException.getMessage().contains("Duplicate entry")) {
			String[] split = sqlException.getMessage().split(" ");
			String errorMsg = split[2] + "出现重复";
			log.info(errorMsg);
			return ReturnResult.sendError(errorMsg);
		}
		return ReturnResult.sendError("出现异常");
	}

	/**
	 * 自定义异常
	 *
	 * @param customException 异常体
	 * @return java中的代码拦截好，抛出到外面
	 */
	@ExceptionHandler(CustomException.class)
	public ReturnResult<String> customExceptionHandler(CustomException customException) {
		String message = customException.getMessage();
		return ReturnResult.sendError(message);
	}


}
