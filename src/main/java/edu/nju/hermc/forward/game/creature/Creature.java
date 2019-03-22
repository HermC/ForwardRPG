package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.bag.Prop;

import java.io.Serializable;


public abstract class Creature implements Serializable {

    protected double x;
    protected double y;

    protected int hp;   // 生命值
    protected int mp;   // 魔法值
    protected int ap;   // 行动点数

    protected Bag bag;
    protected Buff buff = null;

//    public Creature(int hp,int mp,int ap){
//        this.hp = hp;
//        this.mp = mp;
//        this.ap = ap;
//    }
    public Creature(int hp,int mp,int ap, Bag bag){
        this.hp = hp;
        this.mp = mp;
        this.ap = ap;
        this.bag = bag;
    }

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


    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public boolean addBuff(Buff buff){
        buff.setNextBuff(this.buff);
        this.buff = buff;
        return true;
    }


}
