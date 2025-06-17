package top.ptcc9.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.ptcc9.controller.vo.WorkOrderReqVO;
import top.ptcc9.entity.WorkOrder;
import top.ptcc9.service.WorkOrderService;
import top.ptcc9.mapper.WorkOrderMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
* @author NWT
* @description 针对表【work_order(维修工单表)】的数据库操作Service实现
* @createDate 2025-06-09 23:41:55
*/
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder>
    implements WorkOrderService{

    @Resource
    private WorkOrderMapper workOrderMapper;

    @Override
    public List<WorkOrderReqVO> getList(WorkOrderReqVO rt) {
        if(rt.getPage() == null || rt.getPageSize() == null){
            rt.setPage(0);
            rt.setPageSize(-1);
        }
        final List<WorkOrder> workOrders = workOrderMapper.selectLists(rt.getPage(),
                rt.getPageSize(), rt.getStartTime(1L),rt.getEndTime(1L));
        List<WorkOrderReqVO> list = new ArrayList<>();
        for (WorkOrder workOrder : workOrders) {
            WorkOrderReqVO workOrderReqVO = new WorkOrderReqVO();
            workOrderReqVO.setId(workOrder.getId().toString());
            workOrderReqVO.setDeviceName(workOrder.getDeviceName());
            workOrderReqVO.setCreateTime(workOrder.getCreateTime().toString());
            workOrderReqVO.setPhoto(workOrder.getPhotoUrl());
            workOrderReqVO.setStatus(workOrder.getStatus());
            workOrderReqVO.setDescription(workOrder.getDescription());
            list.add(workOrderReqVO);
        }
        return list;
    }

    @Override
    public void update(WorkOrder t) {
        workOrderMapper.update(t);
    }
}




