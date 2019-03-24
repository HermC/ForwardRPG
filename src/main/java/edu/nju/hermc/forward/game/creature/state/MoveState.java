package edu.nju.hermc.forward.game.creature.state;

import edu.nju.hermc.forward.game.creature.Creature;

import java.io.Serializable;

public class MoveState implements State, Serializable {

    @Override
    public boolean doCollide(Creature creature) {
        creature.setState(new FightState());
        return true;
    }

    @Override
    public boolean doFightEnd(Creature creature) {
        return false;
    }

}
