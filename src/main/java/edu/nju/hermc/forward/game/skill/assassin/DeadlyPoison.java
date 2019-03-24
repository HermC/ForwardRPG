package edu.nju.hermc.forward.game.skill.assassin;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.buff.DeadlyPoisonBuff;

public class DeadlyPoison extends Skill{


    public DeadlyPoison(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }

    public DeadlyPoison(int level){
        this.name = "致命毒药";
        this.decription = "";
        this.skillValue = 2;
        this.isBuff = true;
        this.skillBuff = new DeadlyPoisonBuff(level);
        this.level = level;
        this.mp = 0;
        this.ap = 2;
        this.hp = 0;

    }

    @Override
    public int caculateDamage(int damage) {
        return damage;
    }
}
