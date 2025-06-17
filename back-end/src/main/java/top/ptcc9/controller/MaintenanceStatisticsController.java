package top.ptcc9.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ptcc9.common.R;
import top.ptcc9.controller.vo.AccessoryDataReqVO;
import top.ptcc9.service.AccessoryService;

import javax.annotation.Resource;
import java.util.List;

import static top.ptcc9.common.State.SUCCESS;


/**
 * 驾驶舱数据获取
 */
@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class MaintenanceStatisticsController {

    @Resource
    AccessoryService accessoryService;

    // 获取工件信息
    @GetMapping("/getWorkpieceInfo")
    public R<List<AccessoryDataReqVO>> getWorkpieceInfo() {
        return R.build(SUCCESS,accessoryService.getList());
    }
}
