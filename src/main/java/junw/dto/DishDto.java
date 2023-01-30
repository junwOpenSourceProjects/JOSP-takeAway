package junw.dto;

import junw.entity.Dish;
import junw.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.dto
 *
 * @author liujiajun_junw
 * @Date 2023-01-19-20  星期一
 * @description
 */
@Data
public class DishDto extends Dish {
	// dto是传输过程中使用的对象
	// 首先要继承我们的父类，比如说dish
	// 这里传输，是在dish的基础上，多了一个List类型的对象



	List<DishFlavor> flavors = new ArrayList<>();

	private String categoryName;
	private Integer copies;


}
