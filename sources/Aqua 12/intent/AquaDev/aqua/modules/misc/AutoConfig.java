// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.misc;

import events.listeners.EventUpdate;
import events.Event;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class AutoConfig extends Module
{
    public AutoConfig() {
        super("AutoConfig", Type.Misc, "AutoConfig", 0, Category.Misc);
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
        if (e instanceof EventUpdate) {}
    }
}
