package edu.nju.hermc.forward.game.skill.bag;

import edu.nju.hermc.forward.game.skill.Damage;

public abstract class Prop {

    private int level = 0;

    private String name;

    public Prop(int level, String propName){
        this.level = level;
        this.name = propName;
    }

    public abstract int caculateDamage(int damage);

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
