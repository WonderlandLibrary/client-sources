package me.xatzdevelopments.modules.world;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;

public class Timer extends Module
{
    public ModeSetting Mode;
    public NumberSetting Speed;
    
    public Timer() {
        super("Timer", 0, Category.WORLD, null);
        this.Mode = new ModeSetting("Mode", "Mineplex Combat", new String[] { "Mineplex Combat" });
        this.Speed = new NumberSetting("Speed", 1.0, 0.1, 10.0, 0.2);
        this.addSettings(this.Speed);
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            this.mc.timer.timerSpeed = (float)this.Speed.getValue();
        }
    }
}

