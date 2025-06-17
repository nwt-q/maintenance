package top.ptcc9.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.mail.MessagingException;

import top.ptcc9.controller.vo.ReCordUpdataReqVO;
import top.ptcc9.controller.vo.WorkOrderReqVO;
import top.ptcc9.entity.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import static top.ptcc9.common.State.*;

import top.ptcc9.service.RecordService;
import top.ptcc9.utils.*;
import top.ptcc9.annotations.*;
import top.ptcc9.common.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import top.ptcc9.mapper.*;


@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class RecordController {

    @Resource
    private RecordMapper recordMapper;

    @Resource
    private RecordService recordService;


    @LoginRequired
    @RequestMapping(value = "/viewRecordTable",method = RequestMethod.GET)
    public R<List<top.ptcc9.entity.Record>> viewRecordTable(String equipmentName, String categoryName, String userId, String equipmentId) {
        return R.build(SUCCESS,recordService.getlists(equipmentName,categoryName,userId,equipmentId));
    }

    @LoginRequired
    @RequestMapping(value = "/viewRecord",method = RequestMethod.GET)
    public R<List<top.ptcc9.entity.Record>> viewRecord(String equipmentId) {
        return R.build(SUCCESS,recordService.getviewRecord(equipmentId));
    }

    @LoginRequired
    @RequestMapping(value = "/updateRecord",method = RequestMethod.POST)
    public R<Boolean> updateRecord(@RequestBody top.ptcc9.entity.Record record) {
        recordService.updateRecord(record);
        return R.build(SUCCESS_WITH_NOTIFY,"提交成功");
    }

    @LoginRequired
    @RequestMapping(value = "/updateRecord2",method = RequestMethod.POST)
    public R<Boolean> updateRecord2(@RequestBody top.ptcc9.entity.Record record) {
        recordService.updateRecord2(record);
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
        return recordService.createRecord(record);
    }

    // 更新数据
    @LoginRequired
    @RequestMapping(value = "/updataRecord",method = RequestMethod.POST)
    public R<?> UpdataRecord(ReCordUpdataReqVO formdata){
        top.ptcc9.entity.Record rs = recordMapper.selectById(formdata.getId());
        if(rs != null) {
            recordService.UpdataRecord(rs, formdata);
        } else {
            R.build(ERROR,"未找到设备");
        }
        return R.build(SUCCESS,"更新成功");
    }

    // 获取本月工单完成率
    @LoginRequired
    @RequestMapping(value = "/getMonthRate",method = RequestMethod.GET)
    public R<?> getMonthRate(String mobile){
        return R.build(SUCCESS,recordService.getMonthRate(mobile));
    }
    // 获取待处理的数据 已经超时数据 已完成数据
    @LoginRequired
    @RequestMapping(value = "/getRecordData",method = RequestMethod.GET)
    public R<?> getRecord(String mobile){
        return R.build(SUCCESS,recordService.getRecord(mobile));
    }

    // 获取待处理的数据
    @LoginRequired
    @RequestMapping(value = "/getRecordData2",method = RequestMethod.GET)
    public R<List<WorkOrderReqVO>> getRecord2(String mobile){
        return R.build(SUCCESS,recordService.getRecord2(mobile));
    }

    //TODO: 获取超时未完成工单 ? 还是超时工单 待定

}