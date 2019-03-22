package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.bag.Bag;

public class Mage extends Player {

    public Mage(int hp, int mp, int ap, int level, Bag bag, String playerID) {
        super(hp, mp, ap, level, bag, playerID);
    }

    public Mage(int hp, int mp, int ap, int level, Bag bag) {
        super(hp, mp, ap, level, bag);
    }
}
