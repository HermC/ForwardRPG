package edu.nju.hermc.forward.game.skill.mage;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;

public class AranceBomb extends Skill{



    public AranceBomb(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level) {
        super(skillName, skillDecription, damageValue,buff,skillBuff,level);
    }


    @Override
    public int caculateDamage(int damage) {
        return 0;
    }
}
