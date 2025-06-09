package top.ptcc9.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import top.ptcc9.controller.vo.KnownledgeRepVO;
import top.ptcc9.entity.Knowledgebase;

import java.time.LocalDateTime;
import java.util.List;

/**
* @author NWT
* @description 针对表【KnowledgeBase】的数据库操作Mapper
* @createDate 2025-06-09 15:41:55
*/
@Mapper
@Repository
public interface KnowledgebaseMapper extends BaseMapper<Knowledgebase> {

    // 分页查询
    List<Knowledgebase> selectByPage(  @Param("name") String name,
                                       @Param("pageStart") int pageStart,
                                       @Param("pageSize") int pageSize,
                                       @Param("startTime") LocalDateTime startTime,
                                       @Param("endTime") LocalDateTime endTime
    );
    @Select("SELECT * FROM  KnowledgeBase WHERE  filename LIKE CONCAT('%',#{name},'%')")
    Knowledgebase selectByPath(String path);

    @Delete("DELETE FROM KnowledgeBase WHERE filename=#{id}")
    void deleteByIdt(String id);

    @Select("SELECT * FROM KnowledgeBase WHERE filename=#{name}")
    Knowledgebase selectByIdy(String name);
}




