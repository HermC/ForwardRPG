package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;

import java.util.List;

public class Mage extends Player {

 

    public Mage(int hp, int mp, int ap, int level, Bag bag, String playerID,List<Skill> skillList) {
        super(hp, mp, ap, level, bag, playerID, skillList);
    }

    public Mage(int hp, int mp, int ap, int level, Bag bag,List<Skill> skillList) {
        super(hp, mp, ap, level, bag,skillList);
    }
}
