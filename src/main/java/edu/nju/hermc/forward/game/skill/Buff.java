package edu.nju.hermc.forward.game.skill;

public abstract class Buff implements Damage{

    protected Buff nextBuff;

    protected String decription;

    protected String name;

    protected int buffValue;


//    public Buff(String buffName, String buffDescription ,Buff nextBuff ){
//        this.nextBuff = nextBuff;
//        this.decription = buffDescription;
//        this.name = buffName;
//
//    }

    public Buff getNextBuff() {
        return nextBuff;
    }

    public void setNextBuff(Buff nextBuff) {
        this.nextBuff = nextBuff;
    }


    public int caculateDamage(int damage){
        return 0;
    }




}
