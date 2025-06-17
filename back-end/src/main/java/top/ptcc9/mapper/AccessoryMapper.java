package top.ptcc9.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ptcc9.controller.vo.AccessoryReqVO;
import top.ptcc9.domain.Accessory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author NWT
* @description 针对表【accessory(配件表)】的数据库操作Mapper
* @createDate 2025-06-10 02:22:33
* @Entity top.ptcc9.domain.Accessory
*/
@Mapper
@Repository
public interface AccessoryMapper extends BaseMapper<Accessory> {

    List<Accessory> selectList(AccessoryReqVO params);

    void updateUsedNum(Integer id, Integer ct);

    List<Accessory> selectAList();
}




