// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.Value;

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
