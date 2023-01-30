package junw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import junw.dto.DishDto;
import junw.entity.Dish;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service
 *
 * @author liujiajun_junw
 * @Date 2023-01-18-58  星期五
 * @description
 */
public interface DishService extends IService<Dish> {

	/**
	 * 新增菜品，同时保存口味
	 *
	 * @param dishDto
	 */
	void saveDishWithFlavor(DishDto dishDto);


}
