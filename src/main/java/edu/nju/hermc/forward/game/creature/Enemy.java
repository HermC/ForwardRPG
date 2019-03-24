package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.warrior.Attack;

import java.util.ArrayList;

public class Enemy extends Creature {

    public Enemy() {}

    public Enemy(String objectId, int hp, int mp, int ap, int level, Bag bag) {
        super(objectId, hp, mp, ap, level, bag);
        int skilllevel  = level / 10 + 1;
        ArrayList<Skill> skillList = new ArrayList<>();
        Attack attack0 = new Attack(skilllevel);
        skillList.add(attack0);
        this.skillList = skillList;
    }



}
