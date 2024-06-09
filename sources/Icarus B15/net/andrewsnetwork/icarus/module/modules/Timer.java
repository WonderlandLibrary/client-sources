// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.PreMotion;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.module.Module;

public class Timer extends Module
{
    private final Value<Float> timer;
    
    public Timer() {
        super("Timer", -8388353, Category.MOVEMENT);
        this.timer = (Value<Float>)new ConstrainedValue("timer_Timer Speed", "timerspeed", 1.0f, 1.0f, 30.0f, this);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion) {
            Timer.mc.timer.timerSpeed = this.timer.getValue();
        }
    }
    
    @Override
    public void onDisabled() {
        super.onDisabled();
        Timer.mc.timer.timerSpeed = 1.0f;
    }
}
