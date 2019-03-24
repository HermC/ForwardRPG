package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;

import java.util.List;

public class Player extends Creature{


    protected String playerID;

    public Player() {}

    public Player(int hp, int mp, int ap, int level, Bag bag, String playerID, List<Skill> skillList) {
        super(hp, mp, ap, level, bag,skillList);
        this.playerID = playerID;
    }


    public Player(int hp, int mp, int ap, int level, Bag bag,List<Skill> skillList) {
        super(hp, mp, ap, level, bag, skillList);
    }
}
