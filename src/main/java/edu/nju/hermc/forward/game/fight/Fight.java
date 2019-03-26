package edu.nju.hermc.forward.game.fight;

import edu.nju.hermc.forward.game.creature.Creature;
import edu.nju.hermc.forward.game.creature.Enemy;
import edu.nju.hermc.forward.game.creature.Player;
import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

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
        System.out.println(creature[turnID].getObjectId().equals(objid));
         if ( !creature[turnID].getObjectId().equals(objid) ){
            return null;
        }else {
            System.out.println("in");
            boolean hasConsume = creature[turnID].consume(skill);
            if(hasConsume) {
                if (skill.getType()) {
                    String result_string = "";
                    Buff buff = skill.getSkillBuff().clone();

                    if (skill.getSkillValue() > 0) {

                        creature[turnID].addBuff(buff);
                        result_string += creature[turnID].getObjectId() + "对自己施加了" + buff.getName();
                    } else {
                        int next_id = (turnID + 1) % 2;

                        creature[next_id].addBuff(buff);
                        result_string += creature[turnID].getObjectId() + "对" + creature[next_id].getObjectId()
                                + "施加了" + buff.getName();
                    }
                    turnID = (turnID + 1) % 2;
                    return result_string;
                } else {
                    int source = skill.caculateDamage(0);
                    int damage1 = source;
                    System.out.println(damage1);

                    if (creature[turnID].getBuff() != null) {
                        System.out.println("my buff");
                        damage1 = creature[turnID].getBuff().caculateDamage(source);
                    }

                    if (creature[turnID].getBag().getMyProp() != null) {
                        System.out.println("my weapon");
                        damage1 = creature[turnID].getBag().getMyProp().caculateDamage(damage1);
                    }
                    if (creature[(turnID + 1) % 2].getBuff() != null) {
                        System.out.println("opsite buff");
                        damage1 = creature[(turnID + 1) % 2].getBuff().caculateDamage(damage1);
                    }
                    creature[(turnID + 1) % 2].setCurrent_hp(creature[(turnID + 1) % 2].getCurrent_hp() - damage1);
                    String result_string = "";
                    result_string += creature[turnID].getObjectId() + "对" + creature[(turnID + 1) % 2].getObjectId()
                            + "施加了" + skill.getName() + "，造成了" + String.valueOf(damage1) + "点伤害";
                    turnID = (turnID + 1) % 2;
                    return result_string;
                }
            }else {
                turnID = (turnID + 1) % 2;
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

    public String autoFight(){

        if(creature[1] instanceof Enemy){
            int damage1 = creature[1].getSkillList().get(0).caculateDamage(0);
            creature[0].setCurrent_hp(creature[0].getCurrent_hp() - damage1);
            turnID = (turnID + 1) % 2;
            return "敌人对你造成" + String.valueOf(damage1) + "点伤害";

        }
        return null;

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

    public Creature[] getCreature() {
        return creature;
    }

    public void setCreature(Creature[] creature) {
        this.creature = creature;
    }
}
