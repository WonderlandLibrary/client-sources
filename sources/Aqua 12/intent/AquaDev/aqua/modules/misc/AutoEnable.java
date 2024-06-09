// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.misc;

import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class AutoEnable extends Module
{
    public AutoEnable() {
        super("AutoEnable", Type.Misc, "AutoEnable", 0, Category.Misc);
        Aqua.setmgr.register(new Setting("Velocity", this, false));
        Aqua.setmgr.register(new Setting("Blur", this, true));
        Aqua.setmgr.register(new Setting("Shadow", this, true));
        Aqua.setmgr.register(new Setting("Arraylist", this, true));
        Aqua.setmgr.register(new Setting("ShaderMultiplier", this, true));
        Aqua.setmgr.register(new Setting("ShaderESP", this, true));
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
