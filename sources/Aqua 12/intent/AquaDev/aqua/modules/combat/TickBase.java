// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.combat;

import events.listeners.EventUpdate;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.utils.TimeUtil;
import intent.AquaDev.aqua.modules.Module;

public class TickBase extends Module
{
    public static boolean sneaked;
    TimeUtil timeUtil;
    
    public TickBase() {
        super("TickBase", Type.Combat, "TickBase", 0, Category.Combat);
        this.timeUtil = new TimeUtil();
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate) {
            if (TickBase.mc.gameSettings.keyBindSprint.pressed) {
                TickBase.sneaked = true;
            }
            else {
                TickBase.sneaked = false;
            }
            if (TickBase.sneaked) {
                TickBase.mc.timer.timerSpeed = 0.1f;
                this.timeUtil.reset();
            }
            else {
                TickBase.mc.timer.timerSpeed = 1.5f;
            }
        }
    }
}
