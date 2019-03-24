package edu.nju.hermc.forward.game.map;

import edu.nju.hermc.forward.game.creature.Creature;
import edu.nju.hermc.forward.game.creature.Enemy;
import edu.nju.hermc.forward.game.fight.Fight;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class World {

    private static World WORLD = null;

    private List<Enemy> enemies = new ArrayList<>();

    private Map<String, String> players = new ConcurrentHashMap<>();

    private Map<String, Creature> creatures = new ConcurrentHashMap<>();

    private Map<String, String> clients = new ConcurrentHashMap<>();

    private Map<String, Fight> fights = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        WORLD = this;
        this.initCreatures();
    }

    private World() {

    }

    private void initCreatures() {
        Enemy enemy1 = new Enemy("Enemy1", 20, 20, 20, 1, new Bag(0, null), new ArrayList<>());
        enemy1.setX(80);
        enemy1.setY(100);
        Enemy enemy2 = new Enemy("Enemy2", 30, 30, 20, 2, new Bag(0, null), new ArrayList<>());
        enemy2.setX(100);
        enemy2.setY(140);
        Enemy enemy3 = new Enemy("Enemy3", 40, 40, 20, 3, new Bag(0, null), new ArrayList<>());
        enemy3.setX(100);
        enemy3.setY(180);
        enemies.addAll(Arrays.asList(enemy1, enemy2, enemy3));

        for (Enemy e: enemies) {
            creatures.put(e.getObjectId(), e);
        }

    }

    public static World getInstance() {
        return WORLD == null ? WORLD = new World() : WORLD;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Map<String, Creature> getCreatures() {
        return creatures;
    }

    public Map<String, String> getClients() {
        return clients;
    }

    public Map<String, String> getPlayers() {
        return players;
    }

    public Map<String, Fight> getFights() {
        return fights;
    }

}
