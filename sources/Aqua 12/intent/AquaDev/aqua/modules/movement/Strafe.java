// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import intent.AquaDev.aqua.utils.PlayerUtil;
import events.listeners.EventUpdate;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Strafe extends Module
{
    public Strafe() {
        super("Strafe", Type.Movement, "Strafe", 0, Category.Movement);
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
        if (e instanceof EventUpdate && !Strafe.mc.thePlayer.onGround) {
            PlayerUtil.setSpeed(PlayerUtil.getSpeed());
        }
    }
}
