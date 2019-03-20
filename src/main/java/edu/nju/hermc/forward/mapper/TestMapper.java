package edu.nju.hermc.forward.mapper;

import edu.nju.hermc.forward.game.creature.Enemy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TestMapper {

    @Select("SELECT * FROM test")
    List<Enemy> findAll();

}
