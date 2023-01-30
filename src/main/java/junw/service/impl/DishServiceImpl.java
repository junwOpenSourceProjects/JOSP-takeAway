package junw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.dto.DishDto;
import junw.entity.Dish;
import junw.entity.DishFlavor;
import junw.mapper.DishMapper;
import junw.service.DishFlavorService;
import junw.service.DishService;
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
 * @Date 2023-01-18-59  星期五
 * @description
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

	@Autowired
	private DishFlavorService dishFlavorService;


	/**
	 * 新增菜品，同时保存口味
	 *
	 * @param dishDto 数据体
	 */
	@Override
	@Transactional
	public void saveDishWithFlavor(DishDto dishDto) {
		// 添加注解Transactional
		// 开启事物支持 EnableTransactionManagement

		this.save(dishDto);// 直接调用抽象类service中的方法，将其封好
		// 因为我们的口味列表中，是没有对应的id的，无法映射到具体的菜品中
		// dishFlavorService.saveBatch(dishDto.getFlavors());// 将数据体通过mybatisPlus放进去
		Long dishDtoId = dishDto.getId();
		List<DishFlavor> flavors = dishDto.getFlavors();
		// flavors.f
		// 可以用for循环，但是我们这里采用lambda表达式和stream流
		flavors = flavors.stream().map((item) -> {
			item.setDishId(dishDtoId);
			return item;
		}).collect(Collectors.toList());
		// 说一下这里的处理思路：
		// 首先需要将list中的所有对象，同时添加一个id
		// 所以采用stream流的形式
		// 最后使用collect将其还原为一个list
		dishFlavorService.saveBatch(flavors);
	}
}
