package me.xatzdevelopments.modules.combat;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.util.Timer2;

public class WTap extends Module
{
    public Timer2 timer;
    
    public WTap() {
        super("WTap", 0, Category.COMBAT, null);
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
        if (e instanceof EventMotion && e.isPre() && this.mc.thePlayer.isSprinting() && this.mc.thePlayer.isSwingInProgress) {
            this.mc.thePlayer.setSprinting(this.mc.thePlayer.ticksExisted % 3 == 0);
        }
    }
}
