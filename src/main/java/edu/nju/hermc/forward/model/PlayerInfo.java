package edu.nju.hermc.forward.model;

import lombok.Data;

@Data
public class PlayerInfo {

    private String username;
    private int level;
    private double x;
    private double y;
    private int hp;
    private int mp;
    private int ap;

    private String career;

}
