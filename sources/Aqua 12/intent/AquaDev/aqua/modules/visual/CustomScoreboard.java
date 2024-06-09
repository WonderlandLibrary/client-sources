// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class CustomScoreboard extends Module
{
    public CustomScoreboard() {
        super("CustomScoreboard", Type.Visual, "CustomScoreboard", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Shaders", this, true));
        Aqua.setmgr.register(new Setting("Fade", this, true));
        Aqua.setmgr.register(new Setting("ScorePosY", this, -131.0, -200.0, 215.0, false));
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
