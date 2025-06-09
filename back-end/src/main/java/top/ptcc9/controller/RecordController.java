package top.ptcc9.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.mail.MessagingException;

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
public class RecordController {
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private MaintenanceMapper maintenanceMapper;
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Value("${spring.mail.username}")
    private String sendMailer;

    @LoginRequired
    @RequestMapping(value = "/viewRecordTable",method = RequestMethod.GET)
    public R<List<top.ptcc9.entity.Record>> viewRecordTable(String equipmentName, String categoryName, String userId, String equipmentId) {
        QueryWrapper<top.ptcc9.entity.Record> wrapper = new QueryWrapper<>();
        if (!CommonUtil.isEmpty(equipmentName)) {
            wrapper.like("equipment_name",equipmentName);
        }
        if (!CommonUtil.isEmpty(categoryName)) {
            wrapper.eq("category_name",categoryName);
        }
        if (!CommonUtil.isEmpty(userId)) {
            wrapper.eq("user_id",userId);
        }
        if (!CommonUtil.isEmpty(equipmentId)) {
            wrapper.eq("equipment_id",equipmentId);
        }
        wrapper.orderByDesc("create_time");
        List<top.ptcc9.entity.Record> list = recordMapper.selectList(wrapper);
        return R.build(SUCCESS,list);
    }

    @LoginRequired
    @RequestMapping(value = "/viewRecord",method = RequestMethod.GET)
    public R<List<top.ptcc9.entity.Record>> viewRecord(String equipmentId) {
        QueryWrapper<top.ptcc9.entity.Record> wrapper = new QueryWrapper<>();
        if (!CommonUtil.isEmpty(equipmentId)) {
            wrapper.eq("equipment_id",equipmentId);
        }
        wrapper.orderByDesc("create_time");

        List<top.ptcc9.entity.Record> res = new ArrayList<>();


        List<top.ptcc9.entity.Record> list = recordMapper.selectList(wrapper);
        for (top.ptcc9.entity.Record item : list) {
            if (!CommonUtil.isEmpty(item.getCause())) {
                res.add(item);
            }
        }
        return R.build(SUCCESS,res);
    }

    @LoginRequired
    @RequestMapping(value = "/updateRecord",method = RequestMethod.POST)
    public R<Boolean> updateRecord(@RequestBody top.ptcc9.entity.Record record) {
        Category category = categoryMapper.selectById(record.getCategoryId());
        Maintenance maintenance = maintenanceMapper.selectById(record.getMaintenanceId());
        record.setCategoryName(category.getName());
        record.setMaintenanceName(maintenance.getUsername());

        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(maintenance.getMailbox());
            //邮件主题
            mimeMessageHelper.setSubject("委托维修设备函！");
            //邮件内容
            mimeMessageHelper.setText("我校设备“"+ record.getEquipmentName() + "”" + "于"
                    + CommonUtil.simpleDate2DateTimeAsString(record.getCreateTime())
                    + "经管理员确认发生" + record.getCategoryName()
                    + "! 特委托" + maintenance.getUsername() +"("+ maintenance.getMobile() +")"
                    + "师傅来维修，预祝商祺！");
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());
            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Equipment equipment = equipmentMapper.selectById(record.getEquipmentId());
        equipment.setType("机器故障中");

        equipmentMapper.updateById(equipment);
        recordMapper.updateById(record);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }

    @LoginRequired
    @RequestMapping(value = "/updateRecord2",method = RequestMethod.POST)
    public R<Boolean> updateRecord2(@RequestBody top.ptcc9.entity.Record record) {
        record.setCause(record.getMaintenanceName() + "于"
                + CommonUtil.simpleDate2DateTimeAsString(CommonUtil.getSimpleDateTime())
                + "维修了" + record.getEquipmentName() +"机器，故障原因:" + record.getCause());

        Equipment equipment = equipmentMapper.selectById(record.getEquipmentId());
        equipment.setType("机器状态良好");
        equipmentMapper.updateById(equipment);

        recordMapper.updateById(record);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }

    @LoginRequired
    @RequestMapping(value = "/deleteRecord",method = RequestMethod.POST)
    public R<Boolean> deleteRecord(String id) {
        recordMapper.deleteById(id);
        return R.build(SUCCESS_WITH_NOTIFY,"删除成功");
    }


    @LoginRequired
    @RequestMapping(value = "/createRecord",method = RequestMethod.POST)
    public R<Boolean> createRecord(@RequestBody top.ptcc9.entity.Record record) {
        QueryWrapper<top.ptcc9.entity.Record> wrapper = new QueryWrapper<>();
        wrapper.eq("equipment_id",record.getEquipmentId());
        List<top.ptcc9.entity.Record> records = recordMapper.selectList(wrapper);
        for (top.ptcc9.entity.Record item : records) {
            if (CommonUtil.isEmpty(item.getCause())) {
                return R.build(INFO_WITH_NOTIFY,"该设备正在维护中");
            }
        }

        record.setId(CommonUtil.getSimpleUUID());
        record.setCreateTime(CommonUtil.getSimpleDateTime());
        recordMapper.insert(record);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


}