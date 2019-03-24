package edu.nju.hermc.forward.game.creature.state;

import edu.nju.hermc.forward.game.creature.Creature;

public interface State {

    public boolean doCollide(Creature creature);

    public boolean doFightEnd(Creature creature);

}
