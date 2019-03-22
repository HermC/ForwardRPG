package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.bag.Bag;

public class Player extends Creature{


    protected String playerID;

    public Player(int hp, int mp, int ap, int level, Bag bag, String playerID) {
        super(hp, mp, ap, level, bag);
        this.playerID = playerID;
    }


    public Player(int hp, int mp, int ap, int level, Bag bag) {
        super(hp, mp, ap, level, bag);
    }
}
