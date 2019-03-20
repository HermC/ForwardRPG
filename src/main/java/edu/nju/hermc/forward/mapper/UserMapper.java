package edu.nju.hermc.forward.mapper;

import edu.nju.hermc.forward.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE username = #{username}")
    public User findByUsername(String username);

}
