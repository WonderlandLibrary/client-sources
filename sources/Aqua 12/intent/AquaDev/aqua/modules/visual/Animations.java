// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.client.gui.ScaledResolution;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Animations extends Module
{
    public Animations() {
        super("Animations", Type.Visual, "Animations", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Mode", this, "Aqua", new String[] { "Aqua", "1.7", "Exhibition", "Butter", "High1.7", "Own", "Skidding", "Whack", "Jello", "Snip" }));
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
        final ScaledResolution sr = new ScaledResolution(Animations.mc);
    }
}
