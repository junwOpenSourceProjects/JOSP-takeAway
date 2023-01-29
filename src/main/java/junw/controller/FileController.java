package junw.controller;

import junw.common.ReturnResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-45  星期六
 * @description 文件处理
 */
@RestController
@Slf4j
@RequestMapping("/common")
public class FileController {
	@Value("${reggie.common-path}")
	private String commonPath;

	@PostMapping("/upload")
	public ReturnResult<String> updateFiles(MultipartFile file) {
		log.info("我是上传文件方法" + file.toString());
		String originalFilename = file.getOriginalFilename();// 使用原始文件名
		// 添加一个文件类型，避免乱码
		String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
		// 一般不会使用原始文件名，防止出现重复
		// 这里我们采用UUID的形式，直接拿到一个随机的名称
		String randomName = UUID.randomUUID().toString() + fileType;

		// 判断目录是否存在，如果不存在，就直接创建一个对应的目录出来
		File file1 = new File(commonPath);
		if (!file1.exists()) {
			file1.mkdirs();
		}

		try {
			// 将文件转存到临时位置
			// file.transferTo(new File(commonPath + "demo1.jpg"));
			// file.transferTo(new File(commonPath + originalFilename));
			file.transferTo(new File(commonPath + randomName));
		} catch (IOException e) {
			// throw new RuntimeException(e);
			e.printStackTrace();
		}
		// return ReturnResult.sendSuccess("发送成功");
		// 传递到前端去，避免出现问题
		return ReturnResult.sendSuccess(randomName);
	}

	/**
	 * 文件下载模块
	 *
	 * @param name                文件名
	 * @param httpServletResponse 浏览器响应体
	 */
	@GetMapping("/download")
	public void downloadFile(String name, HttpServletResponse httpServletResponse) {
		try {
			// 先获取读到的内容
			FileInputStream fileInputStream = new FileInputStream(new File(commonPath + name));
			// 拿到一个输出流，将读到的内容写出去
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			httpServletResponse.setContentType("/image/jpeg");// 将图片展示在浏览器中
			// 定义基本的读写参数
			int len = 0;
			byte[] bytes = new byte[1024];// 默认1024长度的数组
			while ((len = fileInputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, len);// 读取完成以后，直接写回去
				outputStream.flush();// 刷新数据
			}
		} catch (Exception e) {
			// throw new RuntimeException(e);
			e.printStackTrace();
		}
	}

}
