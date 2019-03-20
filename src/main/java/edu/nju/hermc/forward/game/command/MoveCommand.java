package edu.nju.hermc.forward.game.command;

import lombok.Data;

@Data
public class MoveCommand {

    private String clientId;

    private double x;
    private double y;

}
