package top.ptcc9.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.ptcc9.entity.*;


@Mapper
@Repository
public interface RecordMapper extends BaseMapper<top.ptcc9.entity.Record> {

}