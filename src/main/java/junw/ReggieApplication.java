package junw;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw
 *
 * @author liujiajun_junw
 * @Date 2023-01-13-22  星期四
 * @description
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {
	// @Slf4j是lombok提供的日志注解
	// 添加了ServletComponentScan注解以后，我们才能扫描到自己的webfilter注解

	public static void main(String[] args) {
		SpringApplication.run(ReggieApplication.class, args);
		log.info("spring已经启动了");
	}

}
