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
        if (this.nextBuff != null) {
            return this.nextBuff.caculateDamage(damage + buffValue);
        }else {
            return  damage + buffValue;
        }
    }


    public Buff clone(){

        DeadlyPoisonBuff deadlyPoisonBuff = new DeadlyPoisonBuff(this.buffValue / 2);
        return deadlyPoisonBuff;
    }
}
