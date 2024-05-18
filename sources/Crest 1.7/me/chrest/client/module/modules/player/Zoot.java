// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.player;

import me.chrest.event.EventTarget;
import java.util.Iterator;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import me.chrest.utils.ClientUtils;
import net.minecraft.potion.PotionEffect;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.item.ItemFood;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "Zoot")
public class Zoot extends Module
{
    Minecraft mc;
    
    public Zoot() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    public void onUpdate(final UpdateEvent event) {
        if (this.mc.thePlayer.isCollidedVertically && (this.mc.thePlayer.getHeldItem() == null || !(this.mc.thePlayer.getHeldItem().getItem() instanceof ItemFood))) {
            for (final Potion potion : Potion.potionTypes) {
                if (potion != null && potion.isBadEffect()) {
                    final PotionEffect effect3 = this.mc.thePlayer.getActivePotionEffect(potion);
                    if (effect3 != null && !effect3.getIsPotionDurationMax()) {
                        for (int index = 0; index < effect3.getDuration(); ++index) {
                            this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(this.mc.thePlayer.onGround));
                            for (final PotionEffect effect4 : this.mc.thePlayer.getActivePotionEffects()) {
                                effect4.deincrementDuration();
                            }
                        }
                    }
                }
            }
            if (this.mc.thePlayer.isBurning() && this.mc.thePlayer.getActivePotionEffect(Potion.fireResistance) == null && ClientUtils.world().getBlockState(new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0, this.mc.thePlayer.posZ)).getBlock().getMaterial() != Material.lava) {
                for (int cleanPotion = 0; cleanPotion < 32; ++cleanPotion) {
                    this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(this.mc.thePlayer.onGround));
                    for (final PotionEffect fireExtinguisher : this.mc.thePlayer.getActivePotionEffects()) {
                        fireExtinguisher.deincrementDuration();
                    }
                }
            }
        }
    }
}
