package junw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import junw.dto.SetmealDto;
import junw.entity.Setmeal;

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
	 * 我是保存一个套餐
	 *
	 * @param setmealDto 实体类
	 */
	void saveOneDish(SetmealDto setmealDto);
}
