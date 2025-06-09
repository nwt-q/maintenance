package top.ptcc9.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.ptcc9.common.R;
import top.ptcc9.controller.vo.AccessoryReqVO;
import top.ptcc9.domain.Accessory;
import top.ptcc9.service.AccessoryService;
import top.ptcc9.mapper.AccessoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
* @author NWT
* @description 针对表【accessory(配件表)】的数据库操作Service实现
* @createDate 2025-06-10 02:22:32
*/
@Service
public class AccessoryServiceImpl extends ServiceImpl<AccessoryMapper, Accessory>
    implements AccessoryService{

    @Resource
    private AccessoryMapper accessoryMapper;

    @Override
    public List<AccessoryReqVO> listAccessories(AccessoryReqVO params) {
        if(params.getPage() > 0) params.setPage((params.getPage() - 1));
        final List<Accessory> accessories = accessoryMapper.selectList(params);
        List<AccessoryReqVO> accessoryReqVO = new ArrayList<>();
        for(Accessory accessory : accessories){
            AccessoryReqVO as = new AccessoryReqVO();
            as.setId(accessory.getId());
            as.setName(accessory.getAccessoryName());
            as.setCodeNumber(accessory.getAccessoryCode());
            as.setUsageQuantity(accessory.getUsedNum());
            as.setStockQuantity(accessory.getStock());
            accessoryReqVO.add(as);
        }
        return accessoryReqVO;
    }
}




