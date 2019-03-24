package edu.nju.hermc.forward.game.skill.mage;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.buff.AranceCurseBuff;

public class Fireball extends Skill{

    public Fireball(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }


    public Fireball(int level){
        this.name = "火球术";
        this.decription = "";
        this.skillValue = 1;
        this.isBuff = false;
        this.skillBuff = null;
        this.level = level;
        this.mp = 2;
        this.ap = 0;
        this.hp = 0;

    }

    @Override
    public int caculateDamage(int damage) {
        return damage + skillValue * level;
    }
}
