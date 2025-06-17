package top.ptcc9.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.ptcc9.entity.Record;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.ptcc9.entity.WorkOrder;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author NWT
* @description 针对表【record】的数据库操作Mapper
* @createDate 2025-06-11 00:44:19
* @Entity top.ptcc9.entity.Record
*/
@Mapper
@Repository
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 分页查询记录列表
     */
    List<Record> selectLists(Integer page, Integer pageSize, String startTime, String endTime, String It);

    /**
     * 更新记录信息
     */
    void update(Record record);

    /**
     * 根据ID查询记录
     */
    Record selectById(String id);

    /**
     * 插入新记录
     */
    int insert(Record record);

    /**
     * 根据ID删除记录
     */
    int deleteById(String id);

    /**
     * 根据id获取
     * @param maintenanceId
     */
    List<Record> getData(String maintenanceId);

    List<Record> getData2(String maintenanceId);
}




