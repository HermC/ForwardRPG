package edu.nju.hermc.forward.game.command;

import edu.nju.hermc.forward.game.creature.Creature;
import lombok.Data;

@Data
public class StateCommand {

    private String result;

    private Creature player;

}
