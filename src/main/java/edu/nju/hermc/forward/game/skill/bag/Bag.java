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

    public String levelupProp(){
        int nowLevel = this.myProp.getLevel();

        int needCoin = nowLevel * 100;
        if(this.coin >= needCoin){
            this.myProp.setLevel(nowLevel + 1);
            this.coin = this.coin - needCoin;
            return "升级成功，当前等级为" + String.valueOf(this.myProp.getLevel());
        }else {
            return "升级失败，金币不足";
        }
    }
}
