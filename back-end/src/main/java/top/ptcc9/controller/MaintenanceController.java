package top.ptcc9.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class MaintenanceController {
    @Resource
    private MaintenanceMapper maintenanceMapper;
    @Resource
    private EquipmentMapper equipmentMapper;
    @Resource
    private RecordMapper recordMapper;
    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @LoginRequired
    @RequestMapping(value = "/sendTextMailMessage",method = RequestMethod.POST)
    public R<Boolean> sendTextMailMessage(String id){
        top.ptcc9.entity.Record record = recordMapper.selectById(id);
        Maintenance maintenance = maintenanceMapper.selectById(record.getMaintenanceId());
        if (CommonUtil.isNull(maintenance)) {
            return R.build(ERROR_WITH_NOTIFY,"查无此人");
        }
        if (CommonUtil.isEmpty(maintenance.getMailbox())) {
            return R.build(ERROR_WITH_NOTIFY,"请为维修人员添加邮箱");
        }
        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人  1或多个
            mimeMessageHelper.setTo(maintenance.getMailbox());
            //邮件主题
            mimeMessageHelper.setSubject("委托维修设备函（催办函）！");
            //邮件内容
            mimeMessageHelper.setText("我校设备“"+ record.getEquipmentName() + "”" + "于"
                    + CommonUtil.simpleDate2DateTimeAsString(record.getCreateTime(1))
                    + "经管理员确认发生" + record.getCategoryName()
                    + "! 请" + maintenance.getUsername() +"("+ maintenance.getMobile() +")"
                    + "师傅尽快来维修，预祝商祺！");
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());

            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            return R.build(SUCCESS_WITH_NOTIFY,"发送成功");

        } catch (MessagingException e) {
            e.printStackTrace();
            return R.build(ERROR_WITH_NOTIFY,"邮件发送失败");
        }
    }


    @LoginRequired
    @RequestMapping(value = "/viewMaintenanceTable",method = RequestMethod.GET)
    public R<List<Maintenance>> viewMaintenanceTable(String username, String mobile, String mailbox) {
        QueryWrapper<Maintenance> wrapper = new QueryWrapper<>();
        if (!CommonUtil.isEmpty(username)) {
            wrapper.like("username",username);
        }
        if (!CommonUtil.isEmpty(mobile)) {
            wrapper.eq("mobile",mobile);
        }
        if (!CommonUtil.isEmpty(mailbox)) {
            wrapper.eq("mailbox",mailbox);
        }
        wrapper.orderByDesc("create_time");
        List<Maintenance> list = maintenanceMapper.selectList(wrapper);
        //TODO:加个Redis
        return R.build(SUCCESS,list);
    }

    @LoginRequired
    @RequestMapping(value = "/viewMaintenanceByEquipment",method = RequestMethod.GET)
    public R<List<Maintenance>> viewMaintenanceByEquipment(String ids) {
        QueryWrapper<Maintenance> wrapper = new QueryWrapper<>();
        List<Maintenance> res = new ArrayList<>();

        if (!CommonUtil.isEmpty(ids)) {
            String[] params = ids.split(",");
            List<String> lists = Arrays.asList(params);
            wrapper.in("id",lists);
            wrapper.orderByDesc("create_time");
            res = maintenanceMapper.selectList(wrapper);
        }
        return R.build(SUCCESS,res);
    }

    @LoginRequired
    @RequestMapping(value = "/viewMaintenanceByEquipment2",method = RequestMethod.GET)
    public R<List<Maintenance>> viewMaintenanceByEquipment2(String id) {
        Equipment equipment = equipmentMapper.selectById(id);
        List<Maintenance> res = new ArrayList<>();
        if (CommonUtil.isNull(equipment)) {
            return R.build(SUCCESS,res);
        }
        QueryWrapper<Maintenance> wrapper = new QueryWrapper<>();

        if (!CommonUtil.isEmpty(equipment.getUserIds())) {
            String[] params = equipment.getUserIds().split(",");
            List<String> lists = Arrays.asList(params);
            wrapper.in("id",lists);
            wrapper.orderByDesc("create_time");
            res = maintenanceMapper.selectList(wrapper);
        }
        return R.build(SUCCESS,res);
    }

    @LoginRequired
    @RequestMapping(value = "/updateMaintenance",method = RequestMethod.POST)
    public R<Boolean> updateMaintenance(@RequestBody Maintenance maintenance) {
        maintenanceMapper.updateById(maintenance);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


    @LoginRequired
    @RequestMapping(value = "/deleteMaintenance",method = RequestMethod.POST)
    public R<Boolean> deleteMaintenance(String id) {
        maintenanceMapper.deleteById(id);
        return R.build(SUCCESS_WITH_NOTIFY,"删除成功");
    }


    @LoginRequired
    @RequestMapping(value = "/createMaintenance",method = RequestMethod.POST)
    public R<Boolean> createMaintenance(@RequestBody Maintenance maintenance) {
        maintenance.setId(CommonUtil.getSimpleUUID());
        maintenance.setCreateTime(CommonUtil.getSimpleDateTime());
        maintenanceMapper.insert(maintenance);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }


}