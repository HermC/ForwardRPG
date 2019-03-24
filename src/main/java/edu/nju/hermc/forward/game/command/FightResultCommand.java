package edu.nju.hermc.forward.game.command;

import edu.nju.hermc.forward.game.creature.Creature;
import lombok.Data;

@Data
public class FightResultCommand {

    private String fightId;

    private Creature ourSide;

    private Creature otherSide;

    private String result;

}
