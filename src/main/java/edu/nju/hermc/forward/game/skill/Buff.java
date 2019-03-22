package edu.nju.hermc.forward.game.skill;

public abstract class Buff implements Damage{

    private Buff nextBuff;

    private String decription;

    private String name;



    public Buff(String buffName, String buffDescription ,Buff nextBuff ){
        this.nextBuff = nextBuff;
        this.decription = buffDescription;
        this.name = buffName;

    }

    public Buff getNextBuff() {
        return nextBuff;
    }

    public void setNextBuff(Buff nextBuff) {
        this.nextBuff = nextBuff;
    }




}
