import junw.ReggieApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:PACKAGE_NAME
 *
 * @author liujiajun_junw
 * @Date 2023-02-15-47  星期六
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ReggieApplication.class)// 必须设置，否则报错
public class SpringBootDataRedisTest {
	@Autowired
	private RedisTemplate redisTemplate;
	// 不设置启动类，上面的这里会无法装载具体的bean

	@Test
	public void testRedisTemplate() {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		valueOperations.set("demo1", "111");// 在底层做了一遍序列化操作
		// 如果没有配置configuration序列化器，那么我们这里出现的就是xxx+key
		// 指定以后，我们设置的demo1，那么结果就是demo1
		System.out.println(valueOperations.get("demo1"));
	}

	@Test
	public void testRedisTemplate2() {
		ValueOperations valueOperations = redisTemplate.opsForValue();
		valueOperations.set("demo1", "111", 10L, TimeUnit.MILLISECONDS);
		// 设置十秒的key，单位都用常量
	}

	@Test
	public void testRedisTemplate3() {
		HashOperations hashOperations = redisTemplate.opsForHash();
		hashOperations.put("demo1", "111", "001");
		hashOperations.put("demo2", "222", "002");
		// Object demo2 = hashOperations.get("demo2", "222");
		// 说是对象，其实都是字符串
		String demo2 = (String) hashOperations.get("demo2", "222");
		System.out.println(demo2);
		// 获取所有键
		for (Object key : hashOperations.keys("demo2")) {
			System.out.println(key);
		}
		// 获取所有的值
		List demo21 = hashOperations.values("demo2");
		for (Object o : demo21) {
			System.out.println(o);
		}
	}


	@Test
	public void testRedisTemplate4() {
		ListOperations listOperations = redisTemplate.opsForList();
		listOperations.leftPush("list1", "demo1");
		listOperations.leftPushAll("list2", "demo2", "demo3", "demo4");

		List<String> listDemo = listOperations.range("listDemo", 0, -1);
		for (String s : listDemo) {
			System.out.println(s);
		}
		System.out.println(listDemo);
		Long listDemo1 = listOperations.size("listDemo");// 拿到key的长度
		int i = listDemo1.intValue();
		for (int j = 0; j < i; j++) {
			Object listDemo2 = listOperations.rightPop("listDemo");
			// 将其弹出队列
			System.out.println(listDemo2);
		}


	}

	@Test
	public void testRedisTemplate5() {
		SetOperations setOperations = redisTemplate.opsForSet();
		setOperations.add("demoSet", "demo1", "demo2", "demo1");
		Set<String> demoSet = setOperations.members("demoSet");// 获取内部的所有set成员
		for (String s : demoSet) {
			System.out.println(s);
		}
		setOperations.remove("demoSet", "demo2");
	}

	@Test
	public void testRedisTemplate6() {
		ZSetOperations zSetOperations = redisTemplate.opsForZSet();
		zSetOperations.add("demoZset", "a", 10.0);
		zSetOperations.add("demoZset", "b", 12.0);
		zSetOperations.add("demoZset", "c", 14.0);
		zSetOperations.add("demoZset", "a", 16.0);
		// 这里是堆内存，先进后出的结构
		// 同时，因为不允许重复元素存在，所以最后一个a没有进入
		Set demoZset = zSetOperations.range("demoZset", 0, -1);
		for (Object o : demoZset) {
			System.out.println(o);
		}
		zSetOperations.incrementScore("demoZset", "a", 22.0);
	}

	@Test
	public void testRedisTemplate7() {
		Set keys = redisTemplate.keys("*");// 获取内部存在的所有元素
		Boolean demoSet = redisTemplate.hasKey("demoSet");// 判断某个key是否存在
		Boolean demoSet1 = redisTemplate.delete("demoSet");// 删除指定的key
		DataType demoSet2 = redisTemplate.type("demoSet");// 判断key的类型
		System.out.println(demoSet2.name());// 给出数据类型的名称

	}
}
