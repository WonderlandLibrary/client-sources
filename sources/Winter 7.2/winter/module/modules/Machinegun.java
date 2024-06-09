/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.world.World;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.Timer;

public class Machinegun
extends Module {
    private Timer shoot = new Timer();

    public Machinegun() {
        super("Instant Bow", Module.Category.Combat, -82952);
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        if (this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && this.mc.gameSettings.keyBindUseItem.pressed && (this.mc.thePlayer.onGround && !this.mc.thePlayer.capabilities.isCreativeMode || this.mc.thePlayer.capabilities.isCreativeMode)) {
            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem());
            int loop = 0;
            while (loop < 20) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(this.mc.thePlayer.onGround));
                ++loop;
            }
            this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
        }
    }
}

