package edu.nju.hermc.forward.game.command;

import lombok.Data;

@Data
public class FightCommand {

    private String username;

    private String fightId;

    private int skillId;

}
