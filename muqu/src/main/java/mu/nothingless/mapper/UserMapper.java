package mu.nothingless.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserMapper<T> extends BaseMapper<T> {

    @Select("SELECT * FROM sys_user WHERE username LIKE CONCAT('%', #{username}, '%')")
    List<T> selectByUsername(@Param("username") String username);
}