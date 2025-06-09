package top.ptcc9.service;

import top.ptcc9.common.R;
import top.ptcc9.controller.vo.AccessoryReqVO;
import top.ptcc9.domain.Accessory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author NWT
* @description 针对表【accessory(配件表)】的数据库操作Service
* @createDate 2025-06-10 02:22:33
*/
public interface AccessoryService extends IService<Accessory> {

    List<AccessoryReqVO> listAccessories(AccessoryReqVO params);
}
