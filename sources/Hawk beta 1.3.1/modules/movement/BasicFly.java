package eze.modules.movement;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;

public class BasicFly extends Module
{
    public BasicFly() {
        super("BasicFly", 34, Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.capabilities.isFlying = false;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            this.mc.thePlayer.capabilities.isFlying = true;
        }
    }
}
