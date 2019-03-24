package edu.nju.hermc.forward.mapper;

import edu.nju.hermc.forward.model.PlayerInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface PlayerMapper {

    @Select("SELECT * from player WHERE username = #{username}")
    PlayerInfo find(String username);

    @Insert("INSERT INTO player VALUES(#{username}, #{level}, #{x}, #{y}, #{hp}, #{mp}, #{ap}, #{career})")
    Integer insert(PlayerInfo info);

    @Update("UPDATE player " +
            "SET level = #{level}, x = #{x}, " +
            "y = #{y}, hp = #{hp}, " +
            "mp = #{mp}, ap = #{ap}, " +
            "career = #{career} WHERE username = #{username}")
    Integer update(PlayerInfo info);

}
