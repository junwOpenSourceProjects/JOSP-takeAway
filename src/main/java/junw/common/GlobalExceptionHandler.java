package junw.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.common
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-15  星期四
 * @description
 */
@ControllerAdvice(annotations = {RestController.class})
@Slf4j
public class GlobalExceptionHandler {
    // ControllerAdvice添加一个controller注解
    // annotations表示对哪些范围生效，有RestController注解的都可以生效
}
