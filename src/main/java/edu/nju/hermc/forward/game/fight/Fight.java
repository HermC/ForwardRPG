package edu.nju.hermc.forward.game.fight;

import edu.nju.hermc.forward.game.creature.Creature;
import edu.nju.hermc.forward.game.skill.Skill;

public class Fight {

    private Creature[] creature = new Creature[2];

    private int turnID;

    public Fight(Creature creatureA , Creature creatureB){
        this.turnID = 0;
        this.creature[0] = creatureA;
        this.creature[1] = creatureB;

    }

    public int playerFight(int creatureid , Skill skill){
        if ( creatureid != turnID ){
            return -10000;
        }else {

            int sourceDamage = skill.getSkillValue();
            if (skill.getType()){
                if(skill.getSkillValue() > 0){
                    creature[creatureid].addBuff(skill.getSkillBuff());
                } else {
                    int next_id = (creatureid+1)%2;
                    creature[next_id].addBuff(skill.getSkillBuff());
                }
                return 0;
            }else {
                int source = skill.getSkillValue();

                int damage1 = creature[creatureid].getBuff().caculateDamage(source);

                damage1 = creature[creatureid].getBag().getMyProp().caculateDamage(damage1);

                damage1 = creature[(creatureid+1)%2].getBuff().caculateDamage(damage1);


                return damage1;
            }




        }


    }

    public int autoFight(){
        return 0;
    }

    public boolean isEnd(){
        if (creature[0].getHp() == 0 || creature[1].getHp() == 0){
            return true;
        }else {
            return false;
        }
    }


}
