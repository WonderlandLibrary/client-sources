// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import me.chrest.utils.ClientUtils;
import me.chrest.event.Event;
import me.chrest.event.events.UpdateEvent;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod(displayName = "Timer")
public class Timer extends Module
{
    @Option.Op(name = "Timer", min = 0.1, max = 5.0, increment = 0.1)
    public float timer;
    
    public Timer() {
        this.timer = 2.0f;
    }
    
    @EventTarget
    public void onTickPre(final UpdateEvent event) {
        final Event.State state = event.getState();
        event.getState();
        if (state == Event.State.PRE) {
            final net.minecraft.util.Timer timer = ClientUtils.mc().timer;
            net.minecraft.util.Timer.timerSpeed = this.timer;
        }
    }
    
    @Override
    public void disable() {
        final net.minecraft.util.Timer timer = ClientUtils.mc().timer;
        net.minecraft.util.Timer.timerSpeed = 1.0f;
        super.disable();
    }
}
