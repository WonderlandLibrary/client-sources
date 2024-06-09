// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class CustomChat extends Module
{
    public CustomChat() {
        super("CustomChat", Type.Visual, "CustomChat", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Alpha", this, 96.0, 0.0, 255.0, false));
        Aqua.setmgr.register(new Setting("Shaders", this, true));
        Aqua.setmgr.register(new Setting("Blur", this, true));
        Aqua.setmgr.register(new Setting("Fade", this, true));
        Aqua.setmgr.register(new Setting("Font", this, true));
        Aqua.setmgr.register(new Setting("Mode", this, "Glow", new String[] { "Glow", "Shadow" }));
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
    public void onEvent(final Event event) {
    }
}
