package junw.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.config
 *
 * @author liujiajun_junw
 * @Date 2023-01-13-36  星期四
 * @description
 */
@Slf4j
@Configuration
public class SpringWebConfiguration extends WebMvcConfigurationSupport {

	/**
	 * 静态资源拦截器
	 *
	 * @param registry reg
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		// super.addResourceHandlers(registry);
		log.info("即将进行静态资源映射");

		registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
	}
}
