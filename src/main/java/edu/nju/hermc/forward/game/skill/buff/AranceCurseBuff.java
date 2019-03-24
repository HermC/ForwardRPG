package edu.nju.hermc.forward.game.skill.buff;

import edu.nju.hermc.forward.game.skill.Buff;

public class AranceCurseBuff extends Buff {
//    public AranceCurseBuff(String buffName, String buffDescription, Buff nextBuff) {
//        super(buffName, buffDescription, nextBuff);
//    }
//

    public AranceCurseBuff(int level){
        this.name = "奥术诅咒";
        this.buffValue = 2 * level;

    }

    public int caculateDamage(int damage){
        return this.nextBuff.caculateDamage(damage + buffValue);
    }
}
