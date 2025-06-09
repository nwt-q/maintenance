package top.ptcc9.controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
public class UserController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;

    @LoginRequired
    @RequestMapping(value = "/viewUserTable",method = RequestMethod.GET)
    public R<List<User>> viewUserTable(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!CommonUtil.isEmpty(username)) {
            wrapper.like("username",username);
        }
        wrapper.orderByDesc("create_time");
        List<User> list = userMapper.selectList(wrapper);
        return R.build(SUCCESS,list);
    }


    @LoginRequired
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    public R<Boolean> updateUser(@RequestBody User user) {
        User add = userMapper.selectById(user.getId());

        if (!add.getMobile().equals(user.getMobile())) {
            User userSelect = userMapper.selectOne(
                    new QueryWrapper<User>()
                            .eq("mobile", user.getMobile())
            );
            if (!CommonUtil.isNull(userSelect)) {
                return R.build(ERROR_WITH_NOTIFY,"账号已存在");
            }
        }

        userMapper.updateById(user);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


    @LoginRequired
    @RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
    public R<Boolean> deleteUser(String id) {
        userMapper.deleteById(id);
        return R.build(SUCCESS_WITH_NOTIFY,"删除成功");
    }


    @RequestMapping(value = "/createUser",method = RequestMethod.POST)
    public R<Boolean> createUser(@RequestBody User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",user.getMobile());
        User userSelect = userMapper.selectOne(wrapper);

        if (!CommonUtil.isNull(userSelect)) {
            return R.build(SUCCESS_WITH_NOTIFY,"账户已存在");
        }

        user.setId(CommonUtil.getSimpleUUID());
        user.setCreateTime(CommonUtil.getSimpleDateTime());
        userMapper.insert(user);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


    @RequestMapping(value = "/doLogin",method = RequestMethod.POST)
    public R<Tuple2<String,User>> doLogin(String mobile, String password) {
        User user = userMapper.selectOne(
            new QueryWrapper<User>()
                .eq("mobile", mobile)
                .eq("password", password)
        );
        if (CommonUtil.isNull(user)) {
            return R.build(ERROR_INVALID_ACCOUNT_PASSWORD);
        }else {
            Map<String, String> map = new HashMap<>(1);
            map.put("id",user.getId());
            String token = jwtUtil.create(map);
            Tuple2<String,User> tuple2 = Tuple2.build(token,user);
            return R.build(SUCCESS,tuple2);
        }
    }

    //查看照片
    // http://127.0.0.1:18083/local2Url?name=
    @RequestMapping(value = "/local2Url",method = RequestMethod.GET)
    public void local2Url(String name, HttpServletResponse response) {
        FileUtil.local2Url(name,response);
    }

    //上传照片
    @RequestMapping(value = "/uploadFile",method = RequestMethod.POST ,consumes = "multipart/form-data")
    public R<Tuple2<String,User>> uploadFile(MultipartFile file) {
        User user = new User();
        Tuple2<String,User> tuple2 = Tuple2.build(FileUtil.upload(file),user);
        return R.build(SUCCESS,tuple2);
    }
}