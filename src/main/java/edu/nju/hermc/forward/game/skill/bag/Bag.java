package edu.nju.hermc.forward.game.skill.bag;

public class Bag {

    protected Prop myProp = null;

    protected int coin;

    public Bag(int coin){
        this.coin = coin;
    }

    public Bag(int coin,Prop myProp){
        this.coin = coin;
        this.myProp = myProp;
    }

    public Prop getMyProp() {
        return myProp;
    }

    public void setMyProp(Prop myProp) {
        this.myProp = myProp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
