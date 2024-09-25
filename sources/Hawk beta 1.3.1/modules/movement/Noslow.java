package eze.modules.movement;

import eze.modules.*;

public class Noslow extends Module
{
    public static boolean isnoslow;
    
    static {
        Noslow.isnoslow = false;
    }
    
    public Noslow() {
        super("Noslow", 0, Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
        Noslow.isnoslow = true;
    }
    
    @Override
    public void onDisable() {
        Noslow.isnoslow = false;
    }
}
