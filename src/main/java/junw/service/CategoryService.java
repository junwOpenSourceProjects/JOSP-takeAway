package junw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import junw.entity.Category;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.service
 *
 * @author liujiajun_junw
 * @Date 2023-01-15-46  星期五
 * @description
 */
public interface CategoryService extends IService<Category> {
    /**
     * 根据id删除菜品，确定是否有关联
     *
     * @param id id
     */
    public void removeCategory(Long id);
}
