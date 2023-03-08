package junw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.common.CustomException;
import junw.dto.SetmealDto;
import junw.entity.Setmeal;
import junw.entity.SetmealDish;
import junw.mapper.SetMealMapper;
import junw.service.SetMealService;
import junw.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

		setmealDishList = setmealDishList.stream().map((item) -> {
			item.setSetmealId(String.valueOf(setmealDto.getId()));
			return item;
		}).collect(Collectors.toList());// 菜品批量保存

		setmealDishService.saveBatch(setmealDishList);// 保存这个list
	}

	/**
	 * 删除套餐
	 *
	 * @param ids id列
	 */
	@Override
	@Transactional
	public void removeWithDish(List<Long> ids) {
		// List<Long> ids
		LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.in(Setmeal::getId, ids);
		lambdaQueryWrapper.eq(Setmeal::getStatus, 1);
		// 要查询出来是否可以删除
		int count = this.count(lambdaQueryWrapper);
		if (count > 0) {
			throw new CustomException("使用中的不允许删除");
		}
		// 先删除setMeal表中的数据
		this.removeByIds(ids);

		LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
		lambdaQueryWrapper1.in(SetmealDish::getSetmealId, ids);
		setmealDishService.remove(lambdaQueryWrapper1);
	}
}
