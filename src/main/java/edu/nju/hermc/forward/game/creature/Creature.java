package edu.nju.hermc.forward.game.creature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.nju.hermc.forward.game.creature.state.MoveState;
import edu.nju.hermc.forward.game.creature.state.State;
import edu.nju.hermc.forward.game.skill.Buff;
import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.bag.Prop;

import java.io.Serializable;
import java.util.List;


public abstract class Creature implements Serializable {

    protected String objectId;

    protected double x;
    protected double y;

    protected int hp;   // 生命值
    protected int mp;   // 魔法值
    protected int ap;   // 行动点数

    protected int current_hp;   // 当前生命值
    protected int current_mp;   // 当前魔法值
    protected int current_ap;   // 当前行动点数

    protected int level = 1;
    protected Bag bag;
    protected Buff buff = null;

    protected List<Skill> skillList;

    @JsonIgnore
    protected State state;

    public Creature() {
        this.state = new MoveState();
    }


    public Creature(String objectId, int hp, int mp, int ap, int level, Bag bag, List<Skill> skillList) {
        this.objectId = objectId;

        this.hp = hp;
        this.mp = mp;
        this.ap = ap;
        this.current_hp = hp;
        this.current_mp = mp;
        this.current_ap = ap;

        this.level = level;
        this.bag = bag;
        this.skillList = skillList;

        this.state = new MoveState();
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public int getCurrent_hp() {
        return current_hp;
    }

    public void setCurrent_hp(int current_hp) {
        this.current_hp = current_hp;
    }

    public int getCurrent_mp() {
        return current_mp;
    }

    public void setCurrent_mp(int current_mp) {
        this.current_mp = current_mp;
    }

    public int getCurrent_ap() {
        return current_ap;
    }

    public void setCurrent_ap(int current_ap) {
        this.current_ap = current_ap;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Skill> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<Skill> skillList) {
        this.skillList = skillList;
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

    public boolean addBuff(Buff buff) {
        buff.setNextBuff(this.buff);
        this.buff = buff;
        return true;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
