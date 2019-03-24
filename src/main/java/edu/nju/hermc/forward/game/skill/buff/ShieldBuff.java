package edu.nju.hermc.forward.game.skill.buff;

import edu.nju.hermc.forward.game.skill.Buff;

public class ShieldBuff extends Buff {
//    public ShieldBuff(String buffName, String buffDescription, Buff nextBuff) {
//        super(buffName, buffDescription, nextBuff);
//    }


    public ShieldBuff(int level){
        this.name = "伤害减免";
        this.buffValue = 2 * level;
    }

    public int caculateDamage(int damage){
        if (this.nextBuff != null){
            return this.nextBuff.caculateDamage(Math.max(damage - buffValue,0));
        }else {
            return Math.max(damage - buffValue,0);
        }
    }
}
