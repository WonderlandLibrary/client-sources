// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.movement;

import events.listeners.EventUpdate;
import events.Event;
import net.minecraft.client.settings.KeyBinding;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Sprint extends Module
{
    public Sprint() {
        super("Sprint", Type.Movement, "Sprint", 0, Category.Movement);
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        KeyBinding.setKeyBindState(Sprint.mc.gameSettings.keyBindSprint.getKeyCode(), false);
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventUpdate && Sprint.mc.gameSettings.keyBindForward.pressed) {
            Sprint.mc.thePlayer.setSprinting(true);
        }
    }
}
