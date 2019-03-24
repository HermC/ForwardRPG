package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;

import java.util.ArrayList;
import java.util.List;

public class Warrior extends Player {

    public Warrior() {
        this.career = "战士";
    }

    public Warrior(String objectId, int hp, int mp, int ap, int level, Bag bag) {
        super(objectId, hp, mp, ap, level, bag);

        int skilllevel  = level / 10;
        ArrayList<Skill> skillList = new ArrayList<>();



        this.skillList = skillList;
        this.career = "战士";
    }

}

