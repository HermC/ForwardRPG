package edu.nju.hermc.forward;

import edu.nju.hermc.forward.game.creature.Assassin;
import edu.nju.hermc.forward.game.creature.Creature;
import edu.nju.hermc.forward.game.creature.Enemy;
import edu.nju.hermc.forward.game.creature.Warrior;
import edu.nju.hermc.forward.game.fight.Fight;
import edu.nju.hermc.forward.game.skill.bag.Bag;
import edu.nju.hermc.forward.game.skill.bag.Weapon;
import edu.nju.hermc.forward.game.skill.warrior.Attack;
import org.json.JSONObject;
import org.junit.Test;

public class fightTest {

    @Test
    public void test(){

        Bag bagA = new Bag(10,new Weapon(1,"测试的剑"));
        Bag bagB = new Bag(10);
//        Creature A = new Warrior(10,10,0 ,bagA);
//        Creature B = new Enemy(20,10,0,bagB);
//        Fight fight = new Fight(A,B);
//        int damage = fight.playerFight(0,new Attack("sha","123",5,false,null,1));
//        System.out.println(damage);
    }
}
