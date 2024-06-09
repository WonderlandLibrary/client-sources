/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class ZoomEat
extends Module {
    private boolean zoom = false;

    public ZoomEat() {
        super("Zoom Eat", Module.Category.Exploits, -1089367);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (this.mc.thePlayer.getItemInUseDuration() >= 12 && this.isValidItem(this.mc.thePlayer.getCurrentEquippedItem())) {
            int loop = 0;
            while (loop < 22) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(this.mc.thePlayer.onGround));
                ++loop;
            }
            this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.UP));
            this.mc.thePlayer.stopUsingItem();
        }
    }

    private boolean isValidItem(ItemStack itemStack) {
        if (itemStack != null && (itemStack.getItem() instanceof ItemPotion || itemStack.getItem() instanceof ItemBucketMilk || itemStack.getItem() instanceof ItemFood) && this.mc.thePlayer.isUsingItem()) {
            return true;
        }
        return false;
    }
}

