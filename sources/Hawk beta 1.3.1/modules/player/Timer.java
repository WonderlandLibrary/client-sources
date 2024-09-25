package eze.modules.player;

import eze.modules.*;
import eze.settings.*;
import eze.events.*;
import eze.events.listeners.*;

public class Timer extends Module
{
    public NumberSetting Speed;
    
    public Timer() {
        super("Timer", 38, Category.PLAYER);
        this.Speed = new NumberSetting("Speed", 1.0, 0.05, 4.0, 0.05);
        this.addSettings(this.Speed);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            this.mc.timer.timerSpeed = (float)this.Speed.getValue();
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
}
