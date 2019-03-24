package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.assassin.Assassination;
import edu.nju.hermc.forward.game.skill.assassin.DeadlyPoison;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.warrior.Attack;
import edu.nju.hermc.forward.game.skill.warrior.DoubleEdgedSword;
import edu.nju.hermc.forward.game.skill.warrior.Shield;

import java.util.ArrayList;
import java.util.List;

public class Assassin extends Player {

    public Assassin() {
        this.career = "刺客";
    }

//    public Assassin(String objectId, int hp, int mp, int ap, int level, Bag bag) {
//        super(objectId, hp, mp, ap, level, bag);
//
//        int skilllevel  = level / 10;
//        ArrayList<Skill> skillList = new ArrayList<>();
//        Assassination assassination0 = new Assassination(skilllevel);
//        DeadlyPoison deadlyPoison0 =
//
//        skillList.add(attack0);
//        skillList.add(doubleEdgedSword);
//        skillList.add(shield0);
//
//        this.skillList = skillList;
//        this.career = "刺客";
//    }

}
