package edu.nju.hermc.forward.game.skill.buff;

import edu.nju.hermc.forward.game.skill.Buff;

public class DeadlyPoisonBuff extends Buff{
//    public DeadlyPoisonBuff(String buffName, String buffDescription, Buff nextBuff) {
//        super(buffName, buffDescription, nextBuff);
//    }



    public DeadlyPoisonBuff(int level){
        this.name = "致命毒药";
        this.buffValue = 2 * level;

    }


    public int caculateDamage(int damage){

        return this.nextBuff.caculateDamage(damage + buffValue);
    }
}
