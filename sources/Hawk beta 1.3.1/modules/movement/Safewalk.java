package eze.modules.movement;

import eze.modules.*;

public class Safewalk extends Module
{
    public static boolean isEnabled;
    
    static {
        Safewalk.isEnabled = false;
    }
    
    public Safewalk() {
        super("Safewalk", 0, Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        Safewalk.isEnabled = true;
    }
    
    @Override
    public void onDisable() {
        Safewalk.isEnabled = false;
    }
}
