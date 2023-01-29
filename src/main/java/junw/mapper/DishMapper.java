package junw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import junw.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.mapper
 *
 * @author liujiajun_junw
 * @Date 2023-01-18-56  星期五
 * @description
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
