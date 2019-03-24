package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;

import java.util.List;

public class Assassin extends Player {

    public Assassin() {
        this.career = "刺客";
    }

    public Assassin(String objectId, int hp, int mp, int ap, int level, Bag bag,List<Skill> skillList) {
        super(objectId, hp, mp, ap, level, bag,skillList);
        this.career = "刺客";
    }

}
