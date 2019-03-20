package edu.nju.hermc.forward.game.creature;

import java.io.Serializable;

public abstract class Creature implements Serializable {

    protected double x;
    protected double y;

    protected int hp;   // 生命值
    protected int mp;   // 魔法值
    protected int ap;   // 行动点数

    protected int gold;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getAp() {
        return ap;
    }

    public void setAp(int ap) {
        this.ap = ap;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }
}
