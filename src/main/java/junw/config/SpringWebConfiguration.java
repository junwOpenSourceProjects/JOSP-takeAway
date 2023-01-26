package junw.config;

import junw.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

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

    /**
     * 重写我们的消息转换器
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//		super.extendMessageConverters(converters);
//        新增一个转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        设置对象转换器，将java转换为json
        converter.setObjectMapper(new JacksonObjectMapper());
//        将上面手动设置的转换器，添加到webMvc的容器集合中
        converters.add(0,converter);
    }
}
