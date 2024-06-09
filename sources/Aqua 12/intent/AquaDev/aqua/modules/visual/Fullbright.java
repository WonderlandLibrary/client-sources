// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.modules.visual;

import net.minecraft.potion.PotionEffect;
import events.listeners.EventPreMotion;
import events.Event;
import net.minecraft.potion.Potion;
import intent.AquaDev.aqua.modules.Category;
import intent.AquaDev.aqua.modules.Module;

public class Fullbright extends Module
{
    public Fullbright() {
        super("Fullbright", Type.Visual, "Fullbright", 0, Category.Visual);
        System.out.println("Sprint::init");
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        Fullbright.mc.thePlayer.removePotionEffect(Potion.nightVision.getId());
        super.onDisable();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof EventPreMotion) {
            Fullbright.mc.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), Integer.MAX_VALUE));
        }
    }
}
