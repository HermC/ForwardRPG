package edu.nju.hermc.forward.game.skill.assassin;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;

public class Assassination extends Skill {


    public Assassination(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }


    public Assassination(int level){
        this.name = "刺杀";
        this.decription = "";
        this.skillValue = 2;
        this.isBuff = false;
        this.skillBuff = null;
        this.level = level;
        this.mp = 0;
        this.ap = 4;
        this.hp = 0;

    }
    @Override
    public int caculateDamage(int damage) {

        return damage + skillValue * level;
    }
}
