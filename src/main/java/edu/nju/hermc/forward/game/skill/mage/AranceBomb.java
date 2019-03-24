package edu.nju.hermc.forward.game.skill.mage;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;

public class AranceBomb extends Skill{


    public AranceBomb(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {

        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }

    public AranceBomb(int level){
        this.name = "奥术炸弹";
        this.decription = "";
        this.skillValue = 2;
        this.isBuff = false;
        this.skillBuff = null;
        this.level = level;
        this.mp = 6;
        this.ap = 0;
        this.hp = 0;

    }

    @Override
    public int caculateDamage(int damage) {
        return damage + this.skillValue * this.level;
    }
}
