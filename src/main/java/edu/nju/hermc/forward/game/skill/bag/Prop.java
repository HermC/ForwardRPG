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
}
