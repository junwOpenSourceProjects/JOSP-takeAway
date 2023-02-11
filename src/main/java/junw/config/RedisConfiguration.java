package junw.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.config
 *
 * @author liujiajun_junw
 * @Date 2023-02-16-00  星期六
 * @description
 */
@Configuration
public class RedisConfiguration extends CachingConfigurerSupport {
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		// 相当于这里指定了一个序列化器
		// 原始的序列化是jdkSerializa。。。
		// 指定序列化器的好处在于，在linux操作的过程中
		// 我们使用什么key-value，就是什么
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}
}
