package edu.nju.hermc.forward.game.skill.assassin;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;

public class DeadlyPoison extends Skill{


    public DeadlyPoison(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }

    @Override
    public int caculateDamage(int damage) {
        return 0;
    }
}
