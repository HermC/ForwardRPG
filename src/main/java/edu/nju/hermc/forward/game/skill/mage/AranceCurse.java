package edu.nju.hermc.forward.game.skill.mage;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.buff.AranceCurseBuff;

public class AranceCurse extends Skill{

    public AranceCurse(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }


    public AranceCurse(int level){
        this.name = "奥术诅咒";
        this.decription = "";
        this.skillValue = 2;
        this.isBuff = true;
        this.skillBuff = new AranceCurseBuff(level);
        this.level = level;
        this.mp = 4;
        this.ap = 0;
        this.hp = 0;

    }
    @Override
    public int caculateDamage(int damage) {
        return 0;
    }
}
