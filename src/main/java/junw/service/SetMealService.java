package junw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import junw.dto.SetmealDto;
import junw.entity.Setmeal;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service
 *
 * @author liujiajun_junw
 * @Date 2023-01-18-58  星期五
 * @description
 */
public interface SetMealService extends IService<Setmeal> {

	/**
	 * 新增套餐，同时需要保存套餐和菜品的关联关系
	 *
	 * @param setmealDto 实体类
	 */
	void saveOneDish(SetmealDto setmealDto);

	/**
	 * 删除套餐，同时需要删除套餐和菜品的关联数据
	 * @param ids id列
	 */
	void removeWithDish(List<Long> ids);
}
