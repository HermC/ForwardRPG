package edu.nju.hermc.forward.game.fight;

import edu.nju.hermc.forward.game.creature.Creature;
import edu.nju.hermc.forward.game.creature.Enemy;
import edu.nju.hermc.forward.game.creature.Player;
import edu.nju.hermc.forward.game.skill.Skill;

public class Fight {

    private String fightId;

    private Creature[] creature = new Creature[2];

    private int turnID;

    public Fight(String fightId, Creature creatureA , Creature creatureB){
        this.fightId = fightId;

        this.turnID = 0;
        this.creature[0] = creatureA;
        this.creature[1] = creatureB;

    }

    public String playerFight(String objid , Skill skill){

        if ( creature[turnID].getObjectId().equals(objid) ){
            return null;
        }else {
            boolean hasConsume = creature[turnID].consume(skill);
            if(hasConsume) {
                if (skill.getType()) {
                    String result_string = "";
                    if (skill.getSkillValue() > 0) {
                        creature[turnID].addBuff(skill.getSkillBuff());
                        result_string += creature[turnID].getObjectId() + "对自己施加了" + skill.getDecription();
                    } else {
                        int next_id = (turnID + 1) % 2;
                        creature[next_id].addBuff(skill.getSkillBuff());
                        result_string += creature[turnID].getObjectId() + "对" + creature[next_id].getObjectId()
                                + "施加了" + skill.getName();
                    }
                    turnID = (turnID + 1) % 2;
                    return result_string;
                } else {
                    int source = skill.caculateDamage(0);
                    int damage1 = source;

                    if (creature[turnID].getBuff() != null) {
                        damage1 = creature[turnID].getBuff().caculateDamage(source);
                    }

                    if (creature[turnID].getBag().getMyProp() != null) {
                        damage1 = creature[turnID].getBag().getMyProp().caculateDamage(damage1);
                    }
                    if (creature[(turnID + 1) % 2].getBuff() != null) {
                        damage1 = creature[(turnID + 1) % 2].getBuff().caculateDamage(damage1);
                    }
                    turnID = (turnID + 1) % 2;
                    creature[(turnID + 1) % 2].setCurrent_hp(creature[(turnID + 1) % 2].getCurrent_hp() - damage1);
                    String result_string = "";
                    result_string += creature[turnID].getObjectId() + "对" + creature[(turnID + 1) % 2].getObjectId()
                            + "施加了" + skill.getName() + "，造成了" + String.valueOf(damage1) + "点伤害";

                    return result_string;
                }
            }else {
                return "能量不足无法释放";
            }


        }


    }

    public String getFightId() {
        return fightId;
    }

    public void setFightId(String fightId) {
        this.fightId = fightId;
    }

    public int autoFight(){
        return 0;
    }

    public boolean isEnd(){
        if (creature[0].getCurrent_hp() == 0 || creature[1].getCurrent_hp() == 0){
            if (creature[0].getCurrent_hp() == 0){
                if(creature[1] instanceof Player){
                    creature[1].levelup();
                    if(creature[0] instanceof Enemy){
                        creature[1].getBag().setCoin(creature[1].getBag().getCoin() + creature[0].getBag().getCoin());
                    }
                }
            }else{
                if(creature[0] instanceof Player ){
                    creature[0].levelup();
                    if(creature[1] instanceof Enemy){
                        creature[0].getBag().setCoin(creature[0].getBag().getCoin() + creature[1].getBag().getCoin());
                    }
                }
            }
            return true;
        }else {
            return false;
        }
    }


}
