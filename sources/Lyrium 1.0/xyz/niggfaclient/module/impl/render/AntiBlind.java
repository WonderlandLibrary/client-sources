// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.render;

import net.minecraft.potion.Potion;
import java.util.Iterator;
import net.minecraft.potion.PotionEffect;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AntiBlind", description = "AntiBlind", cat = Category.RENDER)
public class AntiBlind extends Module
{
    private final Listener<MotionEvent> motionEventListener;
    
    public AntiBlind() {
        final Iterator<PotionEffect> iterator;
        PotionEffect potion;
        this.motionEventListener = (e -> {
            this.mc.thePlayer.getActivePotionEffects().iterator();
            while (iterator.hasNext()) {
                potion = iterator.next();
                if (this.isPotionBlackListed(potion)) {
                    this.mc.thePlayer.removePotionEffect(potion.getPotionID());
                }
            }
        });
    }
    
    private boolean isPotionBlackListed(final PotionEffect potion) {
        final Integer[] array;
        final Integer[] integerArray = array = new Integer[] { Potion.blindness.getId(), Potion.confusion.getId() };
        final int length = array.length;
        final int n = 0;
        if (n < length) {
            final int id = array[n];
            return id == potion.getPotionID();
        }
        return false;
    }
}
