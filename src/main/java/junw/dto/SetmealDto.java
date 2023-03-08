package junw.dto;

import junw.entity.Setmeal;
import junw.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.dto
 *
 * @author liujiajun_junw
 * @Date 2023-01-19-00  星期二
 * @description
 */
@Data
public class SetmealDto extends Setmeal {
	/**
	 * setMeal列表
	 */
	private List<SetmealDish> setmealDishList;

	/**
	 * 菜品名称
	 */
	private String categoryName;
}
