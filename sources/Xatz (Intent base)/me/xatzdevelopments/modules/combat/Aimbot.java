package me.xatzdevelopments.modules.combat;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.util.Timer2;

public class Aimbot extends Module
{
    public Timer2 timer;
    
    public Aimbot() {
        super("Aimbot", 0, Category.COMBAT, "Adjusts your aim to aim at an enemy");
        this.timer = new Timer2();
    }
    
    @Override
    public void onEnable() {
    }
    
    @Override
    public void onDisable() {
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            this.mc.thePlayer.setSprinting(this.mc.thePlayer.ticksExisted % 3 == 0);
        }
    }
}