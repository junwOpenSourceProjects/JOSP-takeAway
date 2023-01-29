package junw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.common.CustomException;
import junw.entity.Category;
import junw.entity.Dish;
import junw.entity.Setmeal;
import junw.mapper.CategoryMapper;
import junw.service.CategoryService;
import junw.service.DishService;
import junw.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service.impl
 *
 * @author liujiajun_junw
 * @Date 2023-01-15-46  星期五
 * @description
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;

    /**
     * 根据id删除菜品
     *
     * @param id id
     */
    @Override
    public void removeCategory(Long id) {
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(lambdaQueryWrapper);
        if (count > 0) {
            // 抛出一个异常
            throw new CustomException("还有关联菜品，不允许删除");
        }
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.eq(Setmeal::getCategoryId, id);
        int count1 = setMealService.count(lambdaQueryWrapper1);
        if (count1 > 0) {
            throw new CustomException("还有关联套餐，不允许删除");
        }
        super.removeById(id);
    }
}
