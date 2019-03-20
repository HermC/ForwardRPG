package edu.nju.hermc.forward.game.command;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Command {

    private int code;
    private Object data;

}
