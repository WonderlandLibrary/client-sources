package eze.modules.player;

import eze.modules.*;
import eze.events.*;
import eze.events.listeners.*;

public class Fastplace extends Module
{
    public Fastplace() {
        super("Fastplace", 35, Category.PLAYER);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            this.mc.rightClickDelayTimer = 0;
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.rightClickDelayTimer = 6;
    }
}
