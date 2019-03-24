package edu.nju.hermc.forward.game.skill.warrior;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.buff.ShieldBuff;

public class Shield extends Skill {


    public Shield(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }


    public Shield(int level){
        this.name = "盾牌格挡";
        this.decription = "";
        this.skillValue = 0;
        this.isBuff = true;
        this.skillBuff = new ShieldBuff(level);
        this.level = level;
        this.mp = 0;
        this.ap = 0;
        this.hp = 2;
    }
    @Override
    public int caculateDamage(int damage) {
        return 0;
    }
}
