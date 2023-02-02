package junw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import junw.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.mapper
 *
 * @author liujiajun_junw
 * @Date 2023-02-20-45  星期四
 * @description
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
