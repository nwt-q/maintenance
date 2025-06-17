package top.ptcc9.controller;


import org.springframework.web.bind.annotation.*;
import top.ptcc9.common.R;
import top.ptcc9.common.State;
import top.ptcc9.controller.vo.AccessoryAddReqVO;
import top.ptcc9.controller.vo.AccessoryReqVO;
import top.ptcc9.domain.Accessory;
import top.ptcc9.entity.AccessoryUseHistory;
import top.ptcc9.mapper.AccessoryMapper;
import top.ptcc9.mapper.AccessoryUseHistoryMapper;
import top.ptcc9.service.AccessoryService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class AccessoryController {

    // 分页查询接口
    @Resource
    private AccessoryService accessoryService;

    @Resource
    private AccessoryMapper accessoryMapper;

    @Resource
    private AccessoryUseHistoryMapper accessoryUseHistoryMapper;

    /**
     * 分页查询配件列表
     */
    @GetMapping("/accessories/list")
    public R<List<AccessoryReqVO>> list(AccessoryReqVO params) {
        return R.build(State.SUCCESS,accessoryService.listAccessories(params));
    }

    /**
     * 新增配件
     */
    @PostMapping("/accessories/add")
    public R<?> add(AccessoryAddReqVO accessory) {
        Accessory as = new Accessory();
        as.setAccessoryName(accessory.getAccessory_name());
        as.setAccessoryCode(accessory.getAccessory_code());
        as.setStock(Integer.valueOf(accessory.getStock()));
        as.setUsedNum(Integer.valueOf(accessory.getUsed_num()));
        accessoryMapper.insert(as);
        return R.build(State.SUCCESS,"添加成功");
    }

    /**
     * 修改配件
     */
    @PostMapping("/accessories/claim")
    public R<?> update(@RequestBody Accessory accessory) {
//        return accessoryService.updateAccessory(accessory);
        return R.build(State.SUCCESS,"添加成功");
    }

    /**
     * 删除配件
     */
    @DeleteMapping("/accessories/delete/{id}")
    public R<?> delete(@PathVariable Integer id) {
        accessoryMapper.deleteById(id);
       return  R.build(State.SUCCESS,"删除成功");
    }

    /**
     * 领取配件
     */
    @PostMapping("/accessories/claimt")
    public R<?> claimt(@RequestParam Integer id, @RequestParam("claim_quantity") Integer ct) {
        final Accessory accessory = accessoryMapper.selectById(id);
        if(accessory.getStock() < ct) {
            return R.build(State.ERROR,"库存不足");
        }
        // 更新使用记录
        AccessoryUseHistory auh = new AccessoryUseHistory();
        auh.setAccessoryId(id);
        auh.setCreateTime(LocalDateTime.now());
        accessoryUseHistoryMapper.insert(auh);

        // 更新使用次数
        accessoryMapper.updateUsedNum(id,ct);

        return R.build(State.SUCCESS,"领取成功");
    }
}
