package top.ptcc9.controller;


import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ptcc9.common.R;
import top.ptcc9.controller.vo.WorkOrderReqVO;
import top.ptcc9.entity.WorkOrder;
import top.ptcc9.service.RecordService;
import top.ptcc9.service.WorkOrderService;

import javax.annotation.Resource;
import java.util.List;

import static top.ptcc9.common.State.ERROR_INSERT_DUPLICATION_KEY;
import static top.ptcc9.common.State.SUCCESS;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class WorkOrderController {

    @Resource
    private WorkOrderService workOrderService;

    @Resource
    private RecordService recordService;


    @GetMapping("/workOrderList")
    public R<List<WorkOrderReqVO>> workOrderList(WorkOrderReqVO rt){
        System.out.println(rt);
        return R.build(SUCCESS,workOrderService.getList(rt));
    }

    /**
     * 获取待办项
     * @param rt
     * @return
     */
    @GetMapping("/workOrder")
    public R<List<WorkOrderReqVO>> workOrder(WorkOrderReqVO rt){
        return R.build(SUCCESS,recordService.getlist(rt));
    }

   @PostMapping("/updataworkOrder")
   public R<String> createWorkOrder(WorkOrderReqVO workOrder){

       WorkOrder t = workOrderService.getById(workOrder.getId());
       if(t == null){
           return R.build(ERROR_INSERT_DUPLICATION_KEY, "工单不存在");
       }
       BeanUtils.copyProperties(workOrder,t);

       t.setDeviceName(workOrder.getDeviceName());
       t.setStatus(workOrder.getStatus());
       t.setDescription(workOrder.getDescription());
       t.setPhotoUrl(workOrder.getPhoto());
        workOrderService.update(t);
       return R.build(SUCCESS,"更改表结构");
   }
}
