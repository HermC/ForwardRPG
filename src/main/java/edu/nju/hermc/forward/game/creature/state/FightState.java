package edu.nju.hermc.forward.game.creature.state;

import edu.nju.hermc.forward.game.creature.Creature;

import java.io.Serializable;

public class FightState implements State, Serializable {

    @Override
    public boolean doCollide(Creature creature) {
        return false;
    }

    @Override
    public boolean doFightEnd(Creature creature) {
        creature.setState(new MoveState());
        return true;
    }

}
