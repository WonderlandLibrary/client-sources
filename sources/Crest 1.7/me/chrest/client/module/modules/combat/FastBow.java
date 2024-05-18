// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import me.chrest.event.EventTarget;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.Packet;
import me.chrest.utils.PacketUtils;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import me.chrest.event.events.UpdateEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "FastBOW")
public class FastBow extends Module
{
    Minecraft mc;
    
    public FastBow() {
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    private void onPreUpdate(final UpdateEvent event) {
        if (this.isBow(this.mc.thePlayer.getCurrentEquippedItem()) && this.mc.thePlayer.onGround) {
            PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            PacketUtils.sendPacket(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
            for (int x = 0; x < 32; ++x) {
                PacketUtils.sendPacket(new C03PacketPlayer(true));
            }
            PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
            this.mc.thePlayer.stopUsingItem();
        }
    }
    
    private boolean isBow(final ItemStack stack) {
        return stack != null && (this.mc.thePlayer.isUsingItem() && stack.getItem() instanceof ItemBow);
    }
}
