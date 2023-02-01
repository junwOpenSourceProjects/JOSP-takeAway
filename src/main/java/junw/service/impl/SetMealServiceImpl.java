package junw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.dto.SetmealDto;
import junw.entity.Setmeal;
import junw.entity.SetmealDish;
import junw.mapper.SetMealMapper;
import junw.service.SetMealService;
import junw.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service.impl
 *
 * @author liujiajun_junw
 * @Date 2023-01-19-00  星期五
 * @description
 */
@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

	@Autowired
	private SetMealService setMealService;
	@Autowired
	private SetmealDishService setmealDishService;

	/**
	 * 我是保存一个套餐
	 *
	 * @param setmealDto 实体类
	 * @Transactional
	 */
	@Override
	public void saveOneDish(SetmealDto setmealDto) {
		this.save(setmealDto);// 保存setMeal信息
		List<SetmealDish> setmealDishList = setmealDto.getSetmealDishList();

		setmealDishList.stream().map((item) -> {
			item.setSetmealId(String.valueOf(setmealDto.getId()));
			return item;
		}).collect(Collectors.toList());// 菜品批量保存

		setmealDishService.saveBatch(setmealDishList);// 保存这个list
	}
}
