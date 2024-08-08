package me.napoleon.napoline.modules.combat;

import me.napoleon.napoline.junk.values.type.Num;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;

public class Reach extends Mod {
    public static Num<Double> range = new Num<>("Range",3.0,3.0,4.5);

    public Reach(){
        super("Reach", ModCategory.Combat,"Make you able to attack more far players");
        this.addValues(range);
    }
}
