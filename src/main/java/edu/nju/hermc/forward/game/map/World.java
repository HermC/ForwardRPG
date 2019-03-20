package edu.nju.hermc.forward.game.map;

import edu.nju.hermc.forward.game.creature.Enemy;
import edu.nju.hermc.forward.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class World {

    private static World WORLD = null;

    @Autowired
    private TestMapper testMapper;

    @PostConstruct
    public void init() {
        WORLD = this;
        WORLD.testMapper = this.testMapper;

        List<Enemy> enemies = this.testMapper.findAll();
    }

    private World() {

    }

    private void initCreatures() {

    }

//    private void initChests() {
//
//    }

    public static World getInstance() {
        return WORLD == null ? WORLD = new World() : WORLD;
    }

}
