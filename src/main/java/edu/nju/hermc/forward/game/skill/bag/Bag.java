package edu.nju.hermc.forward.game.skill.bag;

import java.io.Serializable;

public class Bag implements Serializable {

    protected Prop myProp = null;

    protected int coin;

    public Bag() {}

    public Bag(int coin) {
        this.coin = coin;
    }

    public Bag(int coin, Prop myProp) {
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
