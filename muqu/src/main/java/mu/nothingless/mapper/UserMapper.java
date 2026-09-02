package mu.nothingless.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import mu.nothingless.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

    /** 根据业务 userId 查询（不是自增主键 id） */
    @Select("SELECT * FROM sys_user WHERE my_user_id = #{userId} AND bool_deleted = 0")
    Optional<UserEntity> selectByUserId(@Param("userId") String userId);

    /** 根据手机号盲索引查询（AES 字段本身不能用于 WHERE） */
    @Select("SELECT * FROM sys_user WHERE my_phone_index = #{phoneIndex} AND bool_deleted = 0")
    Optional<UserEntity> selectByPhoneIndex(@Param("phoneIndex") String phoneIndex);
}