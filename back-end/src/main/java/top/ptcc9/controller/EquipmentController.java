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

import top.ptcc9.mapper.*;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class EquipmentController {
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private JwtUtil jwtUtil;

    @LoginRequired
    @RequestMapping(value = "/viewEquipmentTable",method = RequestMethod.GET)
    public R<List<Equipment>> viewEquipmentTable(String name, String type, String status) {
        QueryWrapper<Equipment> wrapper = new QueryWrapper<>();
        if (!CommonUtil.isEmpty(name)) {
            wrapper.like("name",name);
        }
        if (!CommonUtil.isEmpty(type)) {
            wrapper.eq("type",type);
        }
        if (!CommonUtil.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        wrapper.orderByAsc("create_time");
        List<Equipment> list = equipmentMapper.selectList(wrapper);
        return R.build(SUCCESS,list);
    }


    @LoginRequired
    @RequestMapping(value = "/updateEquipment",method = RequestMethod.POST)
    public R<Boolean> updateEquipment(@RequestBody Equipment equipment) {
        equipmentMapper.updateById(equipment);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }

    @LoginRequired
    @RequestMapping(value = "/updateEquipmentDetail",method = RequestMethod.POST)
    public R<Boolean> updateEquipmentDetail(@RequestBody Equipment equipment) {
        equipment.setUserIds(equipment.getUserIds().substring(0,equipment.getUserIds().length() - 1));
        equipmentMapper.updateById(equipment);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }

    @LoginRequired
    @RequestMapping(value = "/viewEquipment",method = RequestMethod.GET)
    public R<Equipment> viewEquipment(String id) {
        Random random = new Random();
        int min = 50;
        int max = 400;
        int randomNumber = random.nextInt(max - min + 1) + min;

        int min1 = 110;
        int max2 = 220;
        int randomNumber2 = random.nextInt(max2 - min1 + 1) + min1;

        Equipment equipment = equipmentMapper.selectById(id);
        equipment.setCurrent(randomNumber);
        equipment.setVoltage(randomNumber2);

        return R.build(SUCCESS,equipment);
    }

    @LoginRequired
    @RequestMapping(value = "/deleteEquipment",method = RequestMethod.POST)
    public R<Boolean> deleteEquipment(String id) {
        equipmentMapper.deleteById(id);
        return R.build(SUCCESS_WITH_NOTIFY,"删除成功");
    }


    @LoginRequired
    @RequestMapping(value = "/createEquipment",method = RequestMethod.POST)
    public R<Boolean> createEquipment(@RequestBody Equipment equipment) {
        equipment.setId(CommonUtil.getSimpleUUID());
        equipment.setCreateTime(CommonUtil.getSimpleDateTime());
        equipmentMapper.insert(equipment);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


    @RequestMapping(value = "/getAllData",method = RequestMethod.POST)
    public R<Data> getAllData() {
        Data data = new Data();
        List<String> courses = new ArrayList<>();
        List<Integer> rates = new ArrayList<>();

        QueryWrapper<Equipment> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("create_time");
        List<Equipment> equipment = equipmentMapper.selectList(wrapper);

        for (Equipment item : equipment) {
            QueryWrapper<top.ptcc9.entity.Record> wr = new QueryWrapper<>();
            wr.eq("equipment_id",item.getId());
            List<top.ptcc9.entity.Record> records = recordMapper.selectList(wr);
            courses.add(item.getName());
            rates.add(records.size());
        }

        data.setRates(rates);
        data.setCourses(courses);
        //TODO :加个Redis
        return R.build(SUCCESS,data);
    }
}