package junw.common;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.common
 *
 * @author liujiajun_junw
 * @Date 2023-01-20-03  星期五
 * @description 自定义业务异常
 */
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
