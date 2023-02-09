package junw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import junw.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.mapper
 *
 * @author liujiajun_junw
 * @Date 2023-02-16-55  星期四
 * @description
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
