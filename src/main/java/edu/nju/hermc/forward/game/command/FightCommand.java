package edu.nju.hermc.forward.game.command;

import lombok.Data;

@Data
public class FightCommand {

    private String clientId;

    private String fightId;

    private int skillId;

}
