/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.auto;

import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.option.Option;
import me.thekirkayt.event.EventTarget;
import me.thekirkayt.event.events.TickEvent;
import me.thekirkayt.event.events.UpdateEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

@Module.Mod(displayName="AutoGapple")
public class AutoGapple
extends Module {
    private Timer timer = new Timer();
    protected Minecraft mc = Minecraft.getMinecraft();
    @Option.Op(min=0.0, max=100.0, increment=1.0)
    public static double appleHealth = 8.0;
    @Option.Op(min=0.0, max=30.0, increment=0.10000000149011612)
    public double applesPerSec = 1.7999999523162842;

    @EventTarget
    private void onTick(TickEvent event) {
        int count = 0;
        for (int i = 8; i < 45; ++i) {
            ItemStack is;
            Item item;
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() == null || !((item = (is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem()) instanceof ItemAppleGold)) continue;
            count += is.stackSize;
        }
        this.setSuffix("(" + count + ")");
    }

    @EventTarget
    private void onPostUpdate(UpdateEvent event) {
        int slot = this.getGappleInInv();
        if ((double)this.mc.thePlayer.getHealth() < appleHealth && this.timer.delay((float)this.applesPerSec) && slot != -1 && this.mc.thePlayer.onGround) {
            int prevSlot = this.mc.thePlayer.inventory.currentItem;
            if (slot < 9) {
                ClientUtils.packet(new C09PacketHeldItemChange(slot));
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                ClientUtils.packet(new C09PacketHeldItemChange(slot));
                for (int x = 0; x < 32; ++x) {
                    ClientUtils.packet(new C03PacketPlayer(true));
                }
                ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                this.mc.thePlayer.stopUsingItem();
                ClientUtils.packet(new C09PacketHeldItemChange(prevSlot));
                this.mc.playerController.syncCurrentPlayItem();
                this.mc.thePlayer.inventory.currentItem = prevSlot;
            } else {
                this.swap(slot, this.mc.thePlayer.inventory.currentItem + (this.mc.thePlayer.inventory.currentItem < 8 ? 1 : -1) + 36);
                ClientUtils.packet(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem + (this.mc.thePlayer.inventory.currentItem < 8 ? 1 : -1)));
                ClientUtils.packet(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                ClientUtils.packet(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem + (this.mc.thePlayer.inventory.currentItem < 8 ? 1 : -1)));
                for (int x = 0; x < 32; ++x) {
                    ClientUtils.packet(new C03PacketPlayer(this.mc.thePlayer.onGround));
                }
                ClientUtils.packet(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                this.mc.thePlayer.stopUsingItem();
                ClientUtils.packet(new C09PacketHeldItemChange(prevSlot));
                this.mc.playerController.syncCurrentPlayItem();
                this.swap(slot, this.mc.thePlayer.inventory.currentItem + (this.mc.thePlayer.inventory.currentItem < 8 ? 1 : -1) + 36);
            }
            this.timer.reset();
        }
    }

    private void swap(int slotFrom, int slotTo) {
        this.doSwap(slotFrom, slotTo);
    }

    protected void doSwap(int slot, int slotTo) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, this.mc.thePlayer);
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slotTo, 0, 0, this.mc.thePlayer);
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, this.mc.thePlayer);
    }

    private int getGappleInInv() {
        for (int i = 0; i < 35; ++i) {
            Item item;
            ItemStack is;
            if (this.mc.thePlayer.inventory.mainInventory[i] == null || !((item = (is = this.mc.thePlayer.inventory.mainInventory[i]).getItem()) instanceof ItemAppleGold)) continue;
            return i;
        }
        return -1;
    }
}

