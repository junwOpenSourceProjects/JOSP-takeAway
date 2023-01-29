package junw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.entity.Dish;
import junw.mapper.DishMapper;
import junw.service.DishService;
import org.springframework.stereotype.Service;

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
}
