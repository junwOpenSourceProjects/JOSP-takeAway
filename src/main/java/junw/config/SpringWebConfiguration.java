package junw.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import junw.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

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
@EnableSwagger2
@EnableKnife4j
public class SpringWebConfiguration extends WebMvcConfigurationSupport {
	// 添加完成swagger依赖以后，就需要在web的参数配置上添加对应的注解
	// @EnableSwagger2
	// @EnableKnife4j
	//

	@Bean
	public Docket createRestApi() {
		// Docket对象，实际上就是文档对象
		// 用来描述文档信息
		return new Docket(DocumentationType.SWAGGER_2)
				       .apiInfo(apiInfo())
				       .select()
				       .apis(RequestHandlerSelectors.basePackage("junw.controller"))
				       .paths(PathSelectors.any())
				       .build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				       .title("外卖项目")
				       .version("1.0")
				       .description("外卖项目接口文档")
				       .build();
	}


	/**
	 * 静态资源拦截器
	 *
	 * @param registry reg
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		// super.addResourceHandlers(registry);
		log.info("即将进行静态资源映射");
		// 配置静态资源映射，访问接口文档
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

		registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
		registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
	}

	/**
	 * 重写我们的消息转换器
	 *
	 * @param converters
	 */
	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		// super.extendMessageConverters(converters);
		// 新增一个转换器
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// 设置对象转换器，将java转换为json
		converter.setObjectMapper(new JacksonObjectMapper());
		// 将上面手动设置的转换器，添加到webMvc的容器集合中
		converters.add(0, converter);
	}
}
