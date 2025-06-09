package top.ptcc9.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ptcc9.entity.WorkOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author NWT
* @description 针对表【work_order(维修工单表)】的数据库操作Mapper
* @createDate 2025-06-09 23:41:55
* @Entity top.ptcc9.entity.WorkOrder
*/
@Mapper
@Repository
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

    List<WorkOrder> selectLists(Integer page, Integer pageSize, LocalDateTime startTime, LocalDateTime endTime);

    void update(WorkOrder t);
}




