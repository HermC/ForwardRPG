package edu.nju.hermc.forward.game.skill.warrior;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;

public class DoubleEdgedSword extends Skill{

    public DoubleEdgedSword(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level, int hp, int mp, int sp) {
        super(skillName, skillDecription, damageValue, buff, skillBuff, level, hp, mp, sp);
    }



    @Override
    public int caculateDamage(int damage) {
        return 0;
    }
}
