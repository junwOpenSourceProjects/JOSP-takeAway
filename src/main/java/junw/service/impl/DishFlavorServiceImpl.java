package junw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.entity.DishFlavor;
import junw.mapper.DishFlavorMapper;
import junw.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service.impl
 *
 * @author liujiajun_junw
 * @Date 2023-01-22-24  星期日
 * @description
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
