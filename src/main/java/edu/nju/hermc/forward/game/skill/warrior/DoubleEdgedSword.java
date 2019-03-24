package edu.nju.hermc.forward.game.skill.warrior;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;

public class DoubleEdgedSword extends Skill{

    public DoubleEdgedSword(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }

    public DoubleEdgedSword(int level){
        this.name = "双刃剑";
        this.decription = "";
        this.skillValue = 4;
        this.isBuff = false;
        this.skillBuff = null;
        this.level = level;
        this.mp = 0;
        this.ap = 0;
        this.hp = 2 * level;
    }

    @Override
    public int caculateDamage(int damage) {
        return damage + skillValue * level;
    }
}
