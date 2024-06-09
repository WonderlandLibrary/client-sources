/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;

public class Fastbow
extends Module {
    public Fastbow() {
        super("Fast Bow", Module.Category.Combat, -82952);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (this.mc.thePlayer.getItemInUseDuration() >= 15 && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            int loop = 0;
            while (loop < 25) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(this.mc.thePlayer.onGround));
                ++loop;
            }
            this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
        }
    }
}

