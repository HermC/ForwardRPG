package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.bag.Bag;

public class Enemy extends Creature {

    public Enemy() {}

    public Enemy(String objectId, int hp, int mp, int ap, int level, Bag bag) {
        super(objectId, hp, mp, ap, level, bag);
    }



}
