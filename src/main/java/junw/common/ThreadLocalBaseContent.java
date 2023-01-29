package junw.common;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.common
 *
 * @author liujiajun_junw
 * @Date 2023-01-15-11  星期五
 * @description 单独封好线程中的数据，直接拿到用户信息
 */
public class ThreadLocalBaseContent {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 保存用户id
     *
     * @param id id
     */
    public static void setUserId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 拿到用户id
     *
     * @return id
     */
    public static Long getUserId() {
        return threadLocal.get();
    }
}
