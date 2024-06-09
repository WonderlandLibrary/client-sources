// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import events.listeners.EventUpdate;
import events.Event;
import de.Hero.settings.Setting;
import intent.AquaDev.aqua.Aqua;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class HeldItem extends Module
{
    public static float hitSpeed;
    public static float scale;
    public static float x;
    public static float y;
    public static float z;
    
    public HeldItem() {
        super("HeldItem", Type.Visual, "HeldItem", 0, Category.Visual);
        Aqua.setmgr.register(new Setting("Speed", this, 6.0, 1.0, 30.0, false));
        Aqua.setmgr.register(new Setting("Scale", this, 0.4, 0.1, 1.0, false));
        Aqua.setmgr.register(new Setting("X", this, -0.4, -0.1, 1.0, false));
        Aqua.setmgr.register(new Setting("Y", this, 0.20000000298023224, 0.1, 1.0, false));
        Aqua.setmgr.register(new Setting("Z", this, -0.20000000298023224, -0.1, 1.0, false));
        System.out.println("HeldItem::init");
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
    
    static {
        HeldItem.hitSpeed = 6.0f;
    }
}
