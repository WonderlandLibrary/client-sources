// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import net.minecraft.util.ResourceLocation;
import intent.AquaDev.aqua.modules.Module;

public class CustomCapes extends Module
{
    public static ResourceLocation resourceLocation;
    
    public CustomCapes() {
        super("CustomCapes", Type.Visual, "CustomCapes", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Mode", this, "Aqua", new String[] { "Aqua", "Anime", "Rias", "Rias2" }));
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
