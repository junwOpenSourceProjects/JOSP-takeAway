package junw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import junw.common.ReturnResult;
import junw.entity.Category;
import junw.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Intellij IDEA.
 * Project:reggie_takeaway
 * Package:junw.controller
 *
 * @author liujiajun_junw
 * @Date 2023-01-15-48  星期五
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 我是添加菜单分类
     *
     * @param category
     * @return 成功失败
     */
    @PostMapping
    public ReturnResult<String> saveOneCategory(@RequestBody Category category) {
        log.info("我是添加方法");

        if (category != null) {
            categoryService.save(category);
            return ReturnResult.sendSuccess("添加成功");
        }
        return ReturnResult.sendError("添加失败");
    }

    /**
     * 分页
     *
     * @param page     页面
     * @param pageSize 页面条数
     * @return 分页
     */
    @GetMapping("/page")
    public ReturnResult<Page> page(int page, int pageSize) {
        Page<Category> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByDesc(Category::getSort);
        categoryService.page(page1, lambdaQueryWrapper);
        return ReturnResult.sendSuccess(page1);
    }

    /**
     * 根据id删除一条数据
     *
     * @param id id
     * @return 删除结果
     */
    @DeleteMapping
    public ReturnResult<String> delete(Long id) {
        log.info("我是删除方法，删除id为" + id);
        // categoryService.removeById(id);
        categoryService.removeCategory(id);
        return ReturnResult.sendSuccess("删除成功");
    }

    /**
     * 根据id更新
     *
     * @param category 实体类
     * @return 返回更新成功
     */
    @PutMapping
    public ReturnResult<String> updateById(Category category) {
        categoryService.updateById(category);
        return ReturnResult.sendSuccess("修改分类信息成功");
    }


    @GetMapping("/list")
    public ReturnResult<List<Category>> listCategory(@RequestBody Category category) {
        log.info("修改分类信息" + category);
        return null;
    }


}
