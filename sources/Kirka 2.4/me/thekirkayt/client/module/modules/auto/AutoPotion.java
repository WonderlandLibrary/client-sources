/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.Event;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RotationUtils;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;

@Module.Mod
public class AutoPotion
extends Module {
    @Option.Op(min=0.5, max=10.0, increment=0.5)
    private double health = 4.5;
    @Option.Op(min=200.0, max=2000.0, increment=25.0)
    private double delay = 250.0;
    @Option.Op
    private boolean predict;
    private Timer timer = new Timer();
    private static int expectedPackets;
    private static int potSlot;
    private static ItemStack originalStack;
    private static int currentItem;

    @EventTarget(value=4)
    private void onUpdate(UpdateEvent event) {
        int potSlot;
        if (event.getState() == Event.State.PRE) {
            int potSlot2 = this.getPotionFromInventory();
            if (potSlot2 != -1 && (double)ClientUtils.mc().thePlayer.getHealth() <= this.health * 2.0 && this.timer.delay((float)this.delay)) {
                event.pitch = 90.0f;
                if (this.predict) {
                    double movedPosX = ClientUtils.mc().thePlayer.posX + ClientUtils.mc().thePlayer.motionX * 16.0;
                    double movedPosY = ClientUtils.mc().thePlayer.boundingBox.minY - 3.6;
                    double movedPosZ = ClientUtils.mc().thePlayer.posZ + ClientUtils.mc().thePlayer.motionZ * 16.0;
                    float yaw = RotationUtils.getRotationFromPosition(movedPosX, movedPosZ, movedPosY)[0];
                    float pitch = RotationUtils.getRotationFromPosition(movedPosX, movedPosZ, movedPosY)[1];
                    event.yaw = yaw;
                    event.pitch = pitch;
                }
            }
        } else if (event.getState() == Event.State.POST && (potSlot = this.getPotionFromInventory()) != -1 && (double)ClientUtils.mc().thePlayer.getHealth() <= this.health * 2.0 && this.timer.delay((float)this.delay)) {
            originalStack = ClientUtils.mc().thePlayer.inventoryContainer.getSlot(36 + ClientUtils.mc().thePlayer.inventory.currentItem).getStack();
            AutoPotion.potSlot = potSlot;
            currentItem = ClientUtils.mc().thePlayer.inventory.currentItem;
            boolean blocking = ClientUtils.mc().thePlayer.isBlocking();
            this.swap(potSlot, ClientUtils.mc().thePlayer.inventory.currentItem, true);
            ClientUtils.mc().thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(ClientUtils.mc().thePlayer.getHeldItem()));
            this.swap(potSlot, ClientUtils.mc().thePlayer.inventory.currentItem, true);
            expectedPackets = 4;
            this.timer.reset();
        }
    }

    public static void resetItems() {
        if (expectedPackets > 0) {
            --expectedPackets;
            Minecraft mc = Minecraft.getMinecraft();
            mc.thePlayer.inventoryContainer.putStackInSlot(potSlot, null);
            mc.thePlayer.inventoryContainer.putStackInSlot(36 + currentItem, originalStack);
        }
    }

    protected void swap(int slot, int hotbarNum, boolean packet) {
        ClientUtils.mc().playerController.windowClick(ClientUtils.mc().thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, ClientUtils.mc().thePlayer);
    }

    private int getPotionFromInventory() {
        int pot = -1;
        int counter = 0;
        for (int i = 1; i < 45; ++i) {
            ItemStack is;
            ItemPotion potion;
            Item item;
            if (!ClientUtils.mc().thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((item = (is = ClientUtils.mc().thePlayer.inventoryContainer.getSlot(i).getStack()).getItem()) instanceof ItemPotion) || (potion = (ItemPotion)item).getEffects(is) == null) continue;
            for (Object o : potion.getEffects(is)) {
                PotionEffect effect = (PotionEffect)o;
                if (effect.getPotionID() != Potion.heal.id || !ItemPotion.isSplash(is.getItemDamage())) continue;
                ++counter;
                pot = i;
            }
        }
        return pot;
    }
}

