package junw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import junw.entity.Employee;
import junw.mapper.EmployeeMapper;
import junw.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service.impl
 *
 * @author liujiajun_junw
 * @Date 2023-01-14-34  星期四
 * @description
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
// mybatisPlus提供的接口
// ServiceImpl<EmployeeMapper, Employee>
// 分别指定我们的mapper和实体类
}
