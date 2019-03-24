package edu.nju.hermc.forward.mapper;

import edu.nju.hermc.forward.model.BagInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BagMapper {

    @Select("SELECT * FROM bag WHERE username = #{username}")
    BagInfo find(String username);

    @Insert("INSERT INTO bag VALUES(#{username}, #{coin}, #{prop}, #{propLevel})")
    Integer insert(BagInfo info);

    @Update("UPDATE bag SET coin = #{coin}, prop = #{prop}, propLevel = #{propLevel} WHERE username = #{username}")
    Integer update(BagInfo info);

}
