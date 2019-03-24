package edu.nju.hermc.forward.game.command;

import edu.nju.hermc.forward.game.creature.Creature;
import lombok.Data;

@Data
public class FightInfoCommand {

    String fightId;

    Creature ourSide;

    Creature otherSide;

}
