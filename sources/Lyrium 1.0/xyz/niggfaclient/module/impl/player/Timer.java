// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.UpdateEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Timer", description = "Changes the game tick speed", cat = Category.PLAYER)
public class Timer extends Module
{
    private final DoubleProperty timer;
    @EventLink
    private final Listener<UpdateEvent> updateEventListener;
    
    public Timer() {
        this.timer = new DoubleProperty("Timer", 1.0, 0.1, 10.0, 0.1);
        this.updateEventListener = (e -> this.mc.timer.timerSpeed = this.timer.getValue().floatValue());
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.timer.timerSpeed = 1.0f;
    }
}
