import org.junit.jupiter.api.Test;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:PACKAGE_NAME
 *
 * @author liujiajun_junw
 * @Date 2023-02-15-15  星期六
 * @description
 */
public class Jedis2Redis {
	@Test
	public void testJedis() {
		// Jedis jedis = new Jedis("localhost", 6379);// 获取redis连接
		// jedis.set("userName", "demo1");// 设置键值对
		// String userName = jedis.get("userName");// 和java中的get一样
		// System.out.println("userName:" + userName);
		// // jedis.del("userName");// 删除对应的结果
		// // =======================================================================
		// jedis.hset("demo_hash", "demo1", "111");
		// String hget = jedis.hget("demo_hash", "demo1");
		// System.out.println("hget:" + hget);
		// // =======================================================================
		// Set<String> keys = jedis.keys("*");// 获取所有的键
		// for (String key : keys) {
		// 	System.out.println(key);
		// }
		// System.out.println(keys);// [demo_hash, userName]
		// // =======================================================================
		//SpringBootDataRedisTest
		// // =======================================================================
		// jedis.close();
	}
}
