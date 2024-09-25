package eze.modules.movement;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;

public class Airjump extends Module
{
    public Airjump() {
        super("Airjump", 36, Category.MOVEMENT);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && e.isPre()) {
            this.mc.thePlayer.onGround = true;
        }
    }
}
