package junw.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import junw.common.ReturnResult2;
import junw.entity.QueryDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * Project:JOSP-takeAway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-03-13-50  星期五
 * @description
 */
@RestController
@Slf4j
@Api(tags = "查询测试接口")
@RequestMapping("/queryDemo")
public class DemoController {
	@GetMapping("/page")
	@ApiOperation("queryDemo")
	@ApiImplicitParams({
	})
	public ReturnResult2<Page> page() {
		Page<QueryDemo> page1 = new Page<>();
		List<QueryDemo> records = page1.getRecords();
		QueryDemo queryDemo = new QueryDemo();
		queryDemo.setId(1L);
		queryDemo.setTimestamp("241166362137");
		queryDemo.setAuthor("David");
		queryDemo.setReviewer("Sarah");
		queryDemo.setTitle("Zudoe");
		queryDemo.setContent_short("mock");
		queryDemo.setContent("testing");
		queryDemo.setForecast("26.48");
		queryDemo.setImportance("1");
		queryDemo.setType("JP");
		queryDemo.setStatus("published");
		queryDemo.setDisplay_time("1992-11-09 03:16:44");
		queryDemo.setComment_disabled("true");
		queryDemo.setPageviews("1675");
		queryDemo.setImage_uri("wallstcn");
		records.add(queryDemo);
		page1.setRecords(records);
		// http://localhost:9527/dev-api/vue-element-admin/user/login
		// http://localhost:9527/dev-api/queryDemo/page
		return ReturnResult2.sendSuccess(page1);
	}
}
