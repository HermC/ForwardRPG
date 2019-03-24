package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.Skill;
import edu.nju.hermc.forward.game.skill.bag.Bag;

import java.util.List;

public class Player extends Creature {

    protected String career;

    public Player() {
    }

    public Player(String objectId, int hp, int mp, int ap, int level, Bag bag) {
        super(objectId, hp, mp, ap, level, bag);
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

}
