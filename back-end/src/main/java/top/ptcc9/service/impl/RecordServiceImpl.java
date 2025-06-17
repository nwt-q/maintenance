package top.ptcc9.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import top.ptcc9.common.R;
import top.ptcc9.controller.vo.ReCordUpdataReqVO;
import top.ptcc9.controller.vo.RecordDataReqVO;
import top.ptcc9.controller.vo.WorkOrderReqVO;
import top.ptcc9.entity.*;
import top.ptcc9.entity.Record;
import top.ptcc9.mapper.*;
import top.ptcc9.service.RecordService;
import org.springframework.stereotype.Service;
import top.ptcc9.utils.CommonUtil;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static top.ptcc9.common.State.INFO_WITH_NOTIFY;
import static top.ptcc9.common.State.SUCCESS_WITH_NOTIFY;

/**
* @author NWT
* @description 针对表【record】的数据库操作Service实现
* @createDate 2025-06-11 00:44:19
*/
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
    implements RecordService{

    @Resource
    private RecordMapper recordMapper;

    @Resource
    private MaintenanceMapper maintenanceMapper;

    @Resource
    private EquipmentMapper equipmentMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @Override
    public List<WorkOrderReqVO> getlist(WorkOrderReqVO rt) {
        if(rt.getPage() == null || rt.getPageSize() == null && rt.getPage() > 0){
            rt.setPage(0);
            rt.setPageSize(-1);
        } else {
            rt.setPage(rt.getPage() - 1);
        }
        String maintenanceId = null;
        // 根据电话号码获取 id
        if(rt.getMobile() != null) {
            if(maintenanceMapper.selectByMobile(rt.getMobile()) != null) {
                System.err.println(maintenanceMapper.selectByMobile(rt.getMobile()).getId());
                maintenanceId = maintenanceMapper.selectByMobile(rt.getMobile()).getId();
            }
        }
        System.err.println(rt.getStartTime(1L));

        final List<Record> workOrders = recordMapper.selectLists(rt.getPage(),
                rt.getPageSize(), rt.getStartTime(),rt.getEndTime(), maintenanceId);
        List<WorkOrderReqVO> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (Record record : workOrders) {
            // 根据设备id获取设备地址
            if(map.get(record.getEquipmentId()) == null) {
                map.put(record.getEquipmentId(), equipmentMapper.selectById(record.getEquipmentId()).getLocation());
            }
            WorkOrderReqVO workOrderReqVO = new WorkOrderReqVO();
            workOrderReqVO.setId(record.getId());
            workOrderReqVO.setDeviceName(record.getEquipmentName());
            workOrderReqVO.setCreateTime(record.getCreateTime().toString());
            workOrderReqVO.setPhoto(record.getPic());
            workOrderReqVO.setStatus(record.getStatus());
            workOrderReqVO.setDescription(record.getCause());
            workOrderReqVO.setAddress(map.get(record.getEquipmentId()));
            // 设置紧急程度
            workOrderReqVO.setEmergencyLevel(record.getEmergencyLevel());
            // 设置时限
            workOrderReqVO.setTimeoutMinutes(record.getTimeoutMinutes().toString());
            // 设置categoryName
            workOrderReqVO.setCategoryName(record.getCategoryName());
            list.add(workOrderReqVO);
        }
        return list;
    }

    @Override
    public List<Record> getlists(String equipmentName, String categoryName, String userId, String equipmentId) {
        QueryWrapper<Record> wrapper = new QueryWrapper<>();
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
        return recordMapper.selectList(wrapper);
    }

    @Override
    public List<Record> getviewRecord(String equipmentId) {
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
        return res;
    }

    @Override
    public void updateRecord(Record record) {
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
                    + CommonUtil.simpleDate2DateTimeAsString(record.getCreateTime(1))
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
    }

    @Override
    public void updateRecord2(Record record) {
        record.setCause(record.getMaintenanceName() + "于"
                + CommonUtil.simpleDate2DateTimeAsString(CommonUtil.getSimpleDateTime())
                + "维修了" + record.getEquipmentName() +"机器，故障原因:" + record.getCause());

        Equipment equipment = equipmentMapper.selectById(record.getEquipmentId());
        equipment.setType("机器状态良好");
        equipmentMapper.updateById(equipment);

        recordMapper.updateById(record);
    }

    @Override
    public R<Boolean> createRecord(Record record) {
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

    @Override
    public void UpdataRecord(Record rs, ReCordUpdataReqVO formdata) {
        rs.setEquipmentName(formdata.getDevice_name());
        rs.setPic(formdata.getPhoto());
        rs.setStatus(formdata.getStatus());

        if(rs.getStatus().equals("已完成")){
            rs.setFinishTime(System.currentTimeMillis());
            System.err.println("更新时间: " + rs.getFinishTime(1));
        }
        // 默认为 0
        rs.setIsTimeout(0);

        // 判断是否超时
        if (rs.getStatus().equals("已完成") && rs.getFinishTime() != null) {
            // 数据库中存储的为时间戳
            if (rs.getFinishTime() - rs.getStartTime() > rs.getTimeoutMinutes() * 60 * 1000) {
                rs.setIsTimeout(1);
            }
        }

        rs.setCause(formdata.getDescription());
        recordMapper.update(rs);
    }

    @Override
    public RecordDataReqVO getRecord(String mobile) {
        RecordDataReqVO r = new RecordDataReqVO();
        // 获取待处理的数据 已经超时数据 已完成数据
        String maintenanceId = null;
        // 根据电话号码获取 id
        if(maintenanceMapper.selectByMobile(mobile) != null) {
            System.err.println(maintenanceMapper.selectByMobile(mobile).getId());
            maintenanceId = maintenanceMapper.selectByMobile(mobile).getId();
        }
        // 根据id查询所有表单记录, 统计 待处理的数据 已经超时数据 已完成数据
        final List<Record> data = recordMapper.getData(maintenanceId);
        for(Record record : data){
            if(record.getIsTimeout() != null && record.getIsTimeout() == 1){
                // 更新超时任务
                r.setOverdueCount(((r.getOverdueCount() != null) ? r.getOverdueCount() : 0) + 1);
            } else if(record.getIsTimeout() == null) {
                // 获取当前的时间戳
                long currentTime = System.currentTimeMillis();
                if(currentTime - record.getStartTime() > record.getTimeoutMinutes() * 60 * 1000){
                     // 更新超时任务
                    r.setOverdueCount(((r.getOverdueCount() != null) ? r.getOverdueCount() : 0) + 1);
                }
            }
            // 获取待处理的任务
            if(record.getStatus().equals("进行中")){
                r.setPendingCount((r.getPendingCount() != null ? r.getPendingCount() : 0 ) + 1);
            }
            // 获取已完成的任务
            if(record.getStatus().equals("已完成")){
                r.setCompletedCount((r.getCompletedCount() != null ? r.getCompletedCount() : 0) + 1);
            }
        }
        return r;
    }

    @Override
    public List<WorkOrderReqVO> getRecord2(String mobile) {
        String maintenanceId = null;
        if(maintenanceMapper.selectByMobile(mobile) != null) {
            System.err.println(maintenanceMapper.selectByMobile(mobile).getId());
            maintenanceId = maintenanceMapper.selectByMobile(mobile).getId();
        }

        List<WorkOrderReqVO> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (Record record : recordMapper.getData2(maintenanceId)) {
            // 根据设备id获取设备地址
            if(map.get(record.getEquipmentId()) == null) {
                map.put(record.getEquipmentId(), equipmentMapper.selectById(record.getEquipmentId()).getLocation());
            }
            WorkOrderReqVO workOrderReqVO = new WorkOrderReqVO();
            workOrderReqVO.setId(record.getId());
            workOrderReqVO.setDeviceName(record.getEquipmentName());
            workOrderReqVO.setCreateTime(record.getCreateTime().toString());
            workOrderReqVO.setPhoto(record.getPic());
            workOrderReqVO.setStatus(record.getStatus());
            workOrderReqVO.setDescription(record.getCause());
            workOrderReqVO.setAddress(map.get(record.getEquipmentId()));

            // 设置紧急程度
            workOrderReqVO.setEmergencyLevel(record.getEmergencyLevel());
            // 设置时限
            workOrderReqVO.setTimeoutMinutes(record.getTimeoutMinutes().toString());
            // 设置categoryName
            workOrderReqVO.setCategoryName(record.getCategoryName());

            list.add(workOrderReqVO);
        }
        return list;
    }

    @Override
    public String getMonthRate(String mobile) {
        String maintenanceId = null;
        if(maintenanceMapper.selectByMobile(mobile) != null) {
            System.err.println(maintenanceMapper.selectByMobile(mobile).getId());
            maintenanceId = maintenanceMapper.selectByMobile(mobile).getId();
        }
        int res = 0;
        List<WorkOrderReqVO> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        for (Record record : recordMapper.getData2(maintenanceId)) {
            if(record.getStatus().equals("已完成")) {
                // 并且是本月的
                if(record.getCreateTime().getMonth() == LocalDate.now().getMonth()) {
                    // 根据设备id获取设备地址
                    res++;
                }
            }
        }
        return Integer.toString(res);
    }
}




