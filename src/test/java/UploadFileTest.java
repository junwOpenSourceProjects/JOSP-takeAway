
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:PACKAGE_NAME
 *
 * @author liujiajun_junw
 * @Date 2023-01-16-46  星期六
 * @description
 */
@Slf4j
public class UploadFileTest {
	@Test
	public void test1() {
		String demo = "demo.png";
		String suffix = demo.substring(demo.lastIndexOf("."));
		log.info("我是后缀：" + suffix);
	}
    @Test
    public void show(){
        log.info("123");
    }
}
