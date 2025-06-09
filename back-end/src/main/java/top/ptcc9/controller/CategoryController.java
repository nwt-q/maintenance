package top.ptcc9.controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import top.ptcc9.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import static top.ptcc9.common.State.*;
import top.ptcc9.utils.*;
import top.ptcc9.annotations.*;
import top.ptcc9.common.*;
import java.util.*;
import org.springframework.web.multipart.MultipartFile;
import top.ptcc9.mapper.*;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class CategoryController {
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private JwtUtil jwtUtil;

    @LoginRequired
    @RequestMapping(value = "/viewCategoryTable",method = RequestMethod.GET)
    public R<List<Category>> viewCategoryTable(String name) {
        QueryWrapper<Category> wrapper = new QueryWrapper<>();
        if (!CommonUtil.isEmpty(name)) {
            wrapper.like("name",name);
        }
        wrapper.orderByDesc("create_time");
        //TODO:加个Redis
        List<Category> list = categoryMapper.selectList(wrapper);
        return R.build(SUCCESS,list);
    }


    @LoginRequired
    @RequestMapping(value = "/updateCategory",method = RequestMethod.POST)
    public R<Boolean> updateCategory(@RequestBody Category category) {
        categoryMapper.updateById(category);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


    @LoginRequired
    @RequestMapping(value = "/deleteCategory",method = RequestMethod.POST)
    public R<Boolean> deleteCategory(String id) {
        categoryMapper.deleteById(id);
        return R.build(SUCCESS_WITH_NOTIFY,"删除成功");
    }


    @LoginRequired
    @RequestMapping(value = "/createCategory",method = RequestMethod.POST)
    public R<Boolean> createCategory(@RequestBody Category category) {
        category.setId(CommonUtil.getSimpleUUID());
        category.setCreateTime(CommonUtil.getSimpleDateTime());
        categoryMapper.insert(category);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


}