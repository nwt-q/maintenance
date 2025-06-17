package top.ptcc9.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.ptcc9.entity.*;


@Mapper
@Repository
public interface MaintenanceMapper extends BaseMapper<Maintenance> {

    /**
     * 根据手机号查询用户
     * @param mobile 手机号
     * @return 用户
     */
    @Select("SELECT * FROM maintenance WHERE mobile = #{mobile}")
    Maintenance selectByMobile(String mobile);
}