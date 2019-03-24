package edu.nju.hermc.forward.game.skill.bag;

public class Weapon extends Prop {

    public Weapon(int level, String propName) {
        super(level, propName);
    }

    @Override
    public int caculateDamage(int damage) {
        return damage + this.getLevel();
    }
}
