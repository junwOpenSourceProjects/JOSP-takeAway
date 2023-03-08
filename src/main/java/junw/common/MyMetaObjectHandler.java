package junw.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.common
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-43  星期五
 * @description 元数据接口，实现公共字段自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
	/**
	 * 插入自动填充
	 *
	 * @param metaObject
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("我是插入的时候自动填充的");
		log.info("我是insert元数据对象：" + metaObject.toString());
		metaObject.setValue("createTime", new Date());
		// metaObject.setValue("createUser", new Long(1));// 这里暂时写死，因为我们没办法获取session属性
		metaObject.setValue("createUser", ThreadLocalBaseContent.getUserId());// 从线程中获取用户id
		metaObject.setValue("updateTime", new Date());
		metaObject.setValue("updateUser", ThreadLocalBaseContent.getUserId());
		long threadId = Thread.currentThread().getId();
		log.info("我的线程id：" + threadId);
		// 在我们一次http请求的过程中，实际上都是使用了同一个线程来完成处理
		// 但是每发送一个http请求，在我们服务端都会分派一个线程去完成处理
		// ThreadLocal 之间是线程隔离的，相互不会产生影响
		// 这里获取session信息的方法，是先在login检验方法中，使用ThreadLocal将其保存起来，然后在使用的时候直接set出来
	}

	/**
	 * 更新自动填充
	 *
	 * @param metaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("我是更新的时候自动填充的");
		log.info("我是update元数据对象：" + metaObject.toString());
		metaObject.setValue("updateTime", new Date());
		metaObject.setValue("updateUser", ThreadLocalBaseContent.getUserId());
	}
}
