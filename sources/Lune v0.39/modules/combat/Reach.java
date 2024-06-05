package me.superskidder.lune.modules.combat;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.values.type.Num;

public class Reach extends Mod {
    public static Num<Double> range = new Num<>("Range",3.0,3.0,4.5);

    public Reach(){
        super("Reach", ModCategory.Combat,"Make you able to attack more far players");
        this.addValues(range);
    }
}
