package edu.nju.hermc.forward.game.skill;

public abstract class Skill implements Damage{

    protected String name;
    protected String decription;
    protected boolean isBuff;

    protected int level;


    protected int skillValue;
    protected Buff skillBuff;


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

    public Skill(String skillName, String skillDecription, int damageValue, boolean buff, Buff skillBuff, int level){
        this.name = skillName;
        this.decription = skillDecription;
        this.skillValue = damageValue;
        this.isBuff = buff;
        this.skillBuff = skillBuff;
        this.level = level;
    }


    public boolean getType(){
        return this.isBuff;
    }

}
