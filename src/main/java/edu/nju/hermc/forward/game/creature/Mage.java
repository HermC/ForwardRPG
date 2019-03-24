package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.mage.AranceBomb;
import edu.nju.hermc.forward.game.skill.mage.AranceCurse;
import edu.nju.hermc.forward.game.skill.mage.Fireball;

import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {

 
    public Mage() {
        this.career = "法师";
    }

    public Mage(String objectId, int hp, int mp, int ap, int level, Bag bag) {
        super(objectId, hp, mp, ap, level, bag);
        int skilllevel  = level / 10 + 1;
        ArrayList<Skill> skillList = new ArrayList<>();
        AranceBomb aranceBomb0 = new AranceBomb(skilllevel);
        AranceCurse aranceCurse0 = new AranceCurse(skilllevel);
        Fireball fireball0 = new Fireball(skilllevel);
        skillList.add(aranceBomb0);
        skillList.add(aranceCurse0);
        skillList.add(fireball0);
        this.skillList = skillList;
        this.career = "法师";
    }


    public void levelup(){
        this.level = this.level + 1;
        this.hp = this.hp * 2;
        this.mp = this.mp * 2;
    }
}
