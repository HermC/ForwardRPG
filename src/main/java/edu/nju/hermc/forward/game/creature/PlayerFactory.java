package edu.nju.hermc.forward.game.creature;

import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.bag.Weapon;
import edu.nju.hermc.forward.model.BagInfo;
import edu.nju.hermc.forward.model.PlayerInfo;

public class PlayerFactory {

    public static Player getPlayer(PlayerInfo info) {
        switch (info.getCareer()) {
            case "assassin":
                Player assassin =  new Assassin(info.getUsername(), info.getHp(), info.getMp(), info.getAp(), info.getLevel(), new Bag(0));
                assassin.setX(info.getX());
                assassin.setY(info.getY());
                return assassin;
            case "mage":
                Player mage = new Mage(info.getUsername(), info.getHp(), info.getMp(), info.getAp(), info.getLevel(), new Bag(0));
                mage.setX(info.getX());
                mage.setY(info.getY());
                return mage;
            case "warrior":
                Player warrior =  new Warrior(info.getUsername(), info.getHp(), info.getMp(), info.getAp(), info.getLevel(), new Bag(0));
                warrior.setX(info.getX());
                warrior.setY(info.getY());
                return warrior;
            default:
                return null;
        }
    }

    public static Player getPlayer(PlayerInfo info, BagInfo bagInfo) {
        switch (info.getCareer()) {
            case "assassin":
                Player assassin =  new Assassin(info.getUsername(), info.getHp(), info.getMp(),
                        info.getAp(), info.getLevel(), new Bag(bagInfo.getCoin(), new Weapon(bagInfo.getPropLevel(), bagInfo.getProp())));
                assassin.setX(info.getX());
                assassin.setY(info.getY());
                return assassin;
            case "mage":
                Player mage = new Mage(info.getUsername(), info.getHp(), info.getMp(),
                        info.getAp(), info.getLevel(), new Bag(bagInfo.getCoin(), new Weapon(bagInfo.getPropLevel(), bagInfo.getProp())));
                mage.setX(info.getX());
                mage.setY(info.getY());
                return mage;
            case "warrior":
                Player warrior =  new Warrior(info.getUsername(), info.getHp(), info.getMp(), info.getAp(), info.getLevel(),
                        new Bag(bagInfo.getCoin(), new Weapon(bagInfo.getPropLevel(), bagInfo.getProp())));
                warrior.setX(info.getX());
                warrior.setY(info.getY());
                return warrior;
            default:
                return null;
        }
    }

    public static PlayerInfo getPlayerInfo(String username, String career) {
        PlayerInfo info = new PlayerInfo();
        info.setUsername(username);
        info.setCareer(career);
        info.setHp(50);
        info.setMp(50);
        info.setAp(50);
        info.setLevel(1);
        info.setX(80);
        info.setY(32);
        return info;
    }

}
