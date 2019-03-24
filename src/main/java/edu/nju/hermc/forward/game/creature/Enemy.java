package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;

import java.util.List;

public class Enemy extends Creature{

//    public Enemy(int hp, int mp, int ap, int level, Bag bag) {
//        super(hp, mp, ap, level, bag);
//    }
    public Enemy(int hp, int mp, int ap, int level, Bag bag, List<Skill> skillList) {
        super(hp, mp, ap, level, bag, skillList);
    }
}
