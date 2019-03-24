package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.buff.DeadlyPoisonBuff;
import edu.nju.hermc.forward.game.skill.warrior.Attack;
import edu.nju.hermc.forward.game.skill.warrior.DoubleEdgedSword;
import edu.nju.hermc.forward.game.skill.warrior.Shield;

import java.util.ArrayList;
import java.util.List;

public class Warrior extends Player {

    public Warrior() {
        this.career = "战士";
    }

    public Warrior(String objectId, int hp, int mp, int ap, int level, Bag bag) {
        super(objectId, hp, mp, ap, level, bag);

        int skilllevel  = level / 10 + 1;
        ArrayList<Skill> skillList = new ArrayList<>();
        Attack attack0 = new Attack(skilllevel);
        DoubleEdgedSword doubleEdgedSword = new DoubleEdgedSword(skilllevel);
        Shield shield0 = new Shield(skilllevel);
        skillList.add(attack0);
        skillList.add(doubleEdgedSword);
        skillList.add(shield0);

        this.skillList = skillList;
        this.career = "战士";
    }

    public void levelup(){
        this.level = this.level + 1;
        this.hp = this.hp * 3;
    }

}

