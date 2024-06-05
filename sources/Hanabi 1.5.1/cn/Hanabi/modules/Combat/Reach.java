package cn.Hanabi.modules.Combat;

import ClassSub.*;
import cn.Hanabi.value.*;
import java.util.*;
import cn.Hanabi.modules.*;

public class Reach extends Mod
{
    public long lastAttack;
    public Class205 timer;
    public static Value<Double> reach;
    public Value<Boolean> combo;
    public Random rand;
    public double currentRange;
    
    
    public Reach() {
        super("Reach", Category.COMBAT);
        this.lastAttack = 0L;
        this.timer = new Class205();
        this.combo = new Value<Boolean>("Reach_ComboMode", false);
        this.rand = new Random();
        this.currentRange = 3.0;
    }
    
    public static double getReach() {
        return ModManager.getModule("Reach").getState() ? ((double)(float)(Object)Reach.reach.getValueState()) : 3.0;
    }
    
    static {
        Reach.reach = new Value<Double>("Reach_Reach", 3.0, 3.0, 6.0, 0.1);
    }
}
