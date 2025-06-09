package top.ptcc9.service;

import top.ptcc9.controller.vo.WorkOrderReqVO;
import top.ptcc9.entity.WorkOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author NWT
* @description 针对表【work_order(维修工单表)】的数据库操作Service
* @createDate 2025-06-09 23:41:55
*/
public interface WorkOrderService extends IService<WorkOrder> {

    List<WorkOrderReqVO> getList(WorkOrderReqVO rt);

    void update(WorkOrder t);
}
