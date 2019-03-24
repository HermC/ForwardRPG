package edu.nju.hermc.forward.game.skill;

import java.io.Serializable;

public abstract class Skill implements Damage, Serializable {

    protected String name;
    protected String decription;
    protected boolean isBuff;

    protected int level;


    protected int skillValue;
    protected Buff skillBuff;


    protected int hp;
    protected int mp;
    protected int ap;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getSkillValue() {
        return skillValue;
    }

    public void setSkillValue(int skillValue) {
        this.skillValue = skillValue;
    }

    public boolean isBuff() {
        return isBuff;
    }

    public void setBuff(boolean buff) {
        isBuff = buff;
    }

    public Buff getSkillBuff() {
        return skillBuff;
    }

    public void setSkillBuff(Buff skillBuff) {
        this.skillBuff = skillBuff;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Skill(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level,int hp,int mp,int ap) {
        this.name = skillName;
        this.decription = skillDecription;
        this.skillValue = damageValue;
        this.isBuff = buff;
        this.skillBuff = skillBuff;
        this.level = level;
        this.mp = mp;
        this.hp = hp;
        this.ap = ap;
    }


    public Skill(){

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

    public boolean getType() {
        return this.isBuff;
    }

}
