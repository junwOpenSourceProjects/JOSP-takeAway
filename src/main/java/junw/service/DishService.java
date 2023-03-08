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


	/**
	 * 根据id查询口味和菜品信息
	 *
	 * @param id id
	 * @return dishDto
	 */
	DishDto getDishDtoById(Long id);

	/**
	 * 更新菜品信息
	 *
	 * @param dishDto 实体类
	 */
	void updateDishInfo(DishDto dishDto);
}
