package junw.filter;

import com.alibaba.fastjson.JSON;
import junw.common.ReturnResult;
import junw.common.ThreadLocalBaseContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.filter
 *
 * @author liujiajun_junw
 * @Date 2023-01-12-45  星期四
 * @description
 */
@WebFilter(filterName = "LoginCheckFliter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFliter implements Filter {
	public static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
	// 过滤器，用来拦截登录功能
	// urlPatterns表示拦截的路径，这里设置默认拦截所有路径
	// 拦截javax的servlet

	/**
	 * @param servletRequest
	 * @param servletResponse
	 * @param filterChain
	 * @throws IOException
	 * @throws ServletException
	 */
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;


		long threadId = Thread.currentThread().getId();
		log.info("我的线程id：" + threadId);
		Long userId = (Long) httpServletRequest.getSession().getAttribute("employeeInfo");
		ThreadLocalBaseContent.setUserId(userId);// 直接将session中的数据保存到线程中
		String requestURI = httpServletRequest.getRequestURI();
		String[] requestUrls = {"/employee/login",
				"/employee/logout",
				"/backend/**",
				// 移动端处理的拦截
				"/front/**",
				"/user/sendMsg",
				"user/login",
				// 添加swagger以后补充的拦截
				"/doc.html",
				"webjars/**",
				"/swagger-resources",
				"/v2/api-docs"};
		// "/user/sendMsg"移动端发送短信
		// "user/login"移动端用户登录


		log.info("我是LoginCheckFliter拦截，拦截的请求是{}", httpServletRequest.getRequestURL());
		// {}相当于占位符，直接跟上参数，就可以默认带上后面的变量

		// 判断是否需要处理
		boolean checkLogin = checkLogin(requestUrls, requestURI);
		// 如果不需要处理，就直接返回结果
		if (checkLogin) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			return;
		}
		// 如果需要处理，那么在这里直接放行即可
		if (httpServletRequest.getSession().getAttribute("employeeInfo") != null) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			log.info("登录成功，当前用户为：" + httpServletRequest.getSession().getAttribute("employeeInfo"));
			return;
		}
		// 新增移动端用户的登录操作
		if (httpServletRequest.getSession().getAttribute("user") != null) {
			filterChain.doFilter(httpServletRequest, httpServletResponse);
			log.info("登录成功，当前用户为：" + httpServletRequest.getSession().getAttribute("user"));
			return;
		}
		// httpServletResponse.getWriter().write(JSON.toJSONString(ReturnResult.sendError("登录错误")));
		// 必须按照前端的返回错误信息，来设置这里的消息
		log.info("登录失败");

		httpServletResponse.getWriter().write(JSON.toJSONString(ReturnResult.sendError("NOTLOGIN")));
		// 这里做的其实是，将我们的ReturnResult对象转换为json格式，然后将其写回到前端的消息体中
		return;

	}

	/**
	 * 遍历请求链接
	 *
	 * @param urls 数组
	 * @param url  请求链接
	 * @return 布尔
	 */
	public boolean checkLogin(String[] urls, String requestURI) {
		for (String demoUrl : urls) {
			boolean match = ANT_PATH_MATCHER.match(demoUrl, requestURI);
			log.info("我是LoginCheckFliter请求链接：" + demoUrl);
			if (match) {
				return true;
			}
		}
		return false;
	}
}
