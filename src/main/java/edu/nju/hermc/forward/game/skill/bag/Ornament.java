package edu.nju.hermc.forward.game.skill.bag;

public class Ornament extends Prop{


    public Ornament(int level, String propName) {
        super(level, propName);
    }

    @Override
    public int caculateDamage(int damage) {
        return 0;
    }
}
