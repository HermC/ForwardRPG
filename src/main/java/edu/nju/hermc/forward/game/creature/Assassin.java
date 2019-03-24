package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.assassin.Assassination;
import edu.nju.hermc.forward.game.skill.assassin.DeadlyPoison;
import edu.nju.hermc.forward.game.skill.assassin.Spur;
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

    public Assassin(String objectId, int hp, int mp, int ap, int level, Bag bag) {
        super(objectId, hp, mp, ap, level, bag);

        int skilllevel  = level / 10;
        ArrayList<Skill> skillList = new ArrayList<>();
        Assassination assassination0 = new Assassination(skilllevel);
        DeadlyPoison deadlyPoison0 = new DeadlyPoison(skilllevel);
        Spur spur0 = new Spur(skilllevel);

        skillList.add(assassination0);
        skillList.add(deadlyPoison0);
        skillList.add(spur0);

        this.skillList = skillList;
        this.career = "刺客";
    }
    public void levelup(){
        this.level = this.level + 1;
        this.hp = this.hp * 2;
        this.ap = this.ap * 2;
    }
}
