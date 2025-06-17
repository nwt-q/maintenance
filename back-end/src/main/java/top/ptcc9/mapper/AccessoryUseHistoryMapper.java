package top.ptcc9.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ptcc9.entity.AccessoryUseHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author NWT
* @description 针对表【accessory_use_history(配件使用记录)】的数据库操作Mapper
* @createDate 2025-06-11 00:44:19
* @Entity top.ptcc9.entity.AccessoryUseHistory
*/
@Mapper
@Repository
public interface AccessoryUseHistoryMapper extends BaseMapper<AccessoryUseHistory> {

}




