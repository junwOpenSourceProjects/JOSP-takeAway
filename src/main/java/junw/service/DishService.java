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
	 * 新增菜品，同时插入菜品对应的口味数据，需要操作两张表：dish、dish_flavor
	 *
	 * @param dishDto
	 */
	void saveDishWithFlavor(DishDto dishDto);


	/**
	 * 根据id查询菜品信息和对应的口味信息
	 *
	 * @param id id
	 * @return dishDto
	 */
	DishDto getDishDtoById(Long id);

	/**
	 * 更新菜品信息，同时更新对应的口味信息
	 *
	 * @param dishDto 实体类
	 */
	void updateDishInfo(DishDto dishDto);
}
