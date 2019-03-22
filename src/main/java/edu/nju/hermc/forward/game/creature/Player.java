package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.bag.Bag;

public class Player extends Creature{


    private String playerID;

    private String playerName;


    public Player(int hp, int mp, int ap, Bag bag) {
        super(hp, mp, ap, bag);
    }
}
