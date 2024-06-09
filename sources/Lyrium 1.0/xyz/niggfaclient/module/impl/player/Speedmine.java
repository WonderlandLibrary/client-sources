// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "SpeedMine", description = "SpeedMine", cat = Category.PLAYER)
public class Speedmine extends Module
{
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    
    public Speedmine() {
        EntityPlayerSP thePlayer;
        final PotionEffect potioneffectIn;
        this.motionEventListener = (e -> {
            if (this.mc.thePlayer != null && e.isPre()) {
                this.mc.playerController.blockHitDelay = 0;
                thePlayer = this.mc.thePlayer;
                new PotionEffect(Potion.digSpeed.getId(), 100, (this.mc.thePlayer.getCurrentEquippedItem() == null) ? 1 : 0);
                thePlayer.addPotionEffect(potioneffectIn);
            }
        });
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.removePotionEffect(Potion.digSpeed.getId());
    }
}
