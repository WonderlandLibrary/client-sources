// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.network.Packet;
import me.chrest.utils.PacketUtils;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import me.chrest.event.events.UpdateEvent;
import me.chrest.event.EventTarget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemAppleGold;
import me.chrest.event.events.TickEvent;
import net.minecraft.client.Minecraft;
import me.chrest.client.option.Option;
import me.chrest.utils.TimerUtils;
import me.chrest.client.module.Module;

@Mod
public class AutoGapple extends Module
{
    private TimerUtils timer;
    @Option.Op(min = 0.0, max = 20.0, increment = 1.0)
    public static double appleHealth;
    @Option.Op(min = 0.0, max = 10.0, increment = 1.0)
    public double applesPerSec;
    protected Minecraft mc;
    
    static {
        AutoGapple.appleHealth = 8.0;
    }
    
    public AutoGapple() {
        this.timer = new TimerUtils();
        this.applesPerSec = 1.7999999523162842;
        this.mc = Minecraft.getMinecraft();
    }
    
    @EventTarget
    private void onTick(final TickEvent event) {
        int count = 0;
        for (int i = 8; i < 45; ++i) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null) {
                final ItemStack is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (item instanceof ItemAppleGold) {
                    count += is.stackSize;
                }
            }
        }
        this.setSuffix("(" + count + ")");
    }
    
    @Override
    public void setSuffix(final String string) {
    }
    
    @EventTarget
    private void onPostUpdate(final UpdateEvent event) {
        final int slot = this.getGappleInInv();
        if (this.mc.thePlayer.getHealth() < AutoGapple.appleHealth && this.timer.delay((float)this.applesPerSec) && slot != -1 && this.mc.thePlayer.onGround) {
            final int prevSlot = this.mc.thePlayer.inventory.currentItem;
            if (slot < 9) {
                PacketUtils.sendPacket(new C09PacketHeldItemChange(slot));
                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                PacketUtils.sendPacket(new C09PacketHeldItemChange(slot));
                for (int x = 0; x < 32; ++x) {
                    PacketUtils.sendPacket(new C03PacketPlayer(true));
                }
                PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                this.mc.thePlayer.stopUsingItem();
                PacketUtils.sendPacket(new C09PacketHeldItemChange(prevSlot));
                this.mc.thePlayer.inventory.currentItem = prevSlot;
            }
            else {
                this.swap(slot, this.mc.thePlayer.inventory.currentItem + ((this.mc.thePlayer.inventory.currentItem < 8) ? 1 : -1) + 36);
                PacketUtils.sendPacket(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem + ((this.mc.thePlayer.inventory.currentItem < 8) ? 1 : -1)));
                PacketUtils.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, this.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                PacketUtils.sendPacket(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem + ((this.mc.thePlayer.inventory.currentItem < 8) ? 1 : -1)));
                for (int x = 0; x < 32; ++x) {
                    PacketUtils.sendPacket(new C03PacketPlayer(this.mc.thePlayer.onGround));
                }
                PacketUtils.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.UP));
                this.mc.thePlayer.stopUsingItem();
                PacketUtils.sendPacket(new C09PacketHeldItemChange(prevSlot));
                this.swap(slot, this.mc.thePlayer.inventory.currentItem + ((this.mc.thePlayer.inventory.currentItem < 8) ? 1 : -1) + 36);
            }
            this.timer.reset();
        }
    }
    
    private void swap(final int slotFrom, final int slotTo) {
        this.doSwap(slotFrom, slotTo);
    }
    
    protected void doSwap(final int slot, final int slotTo) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, this.mc.thePlayer);
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slotTo, 0, 0, this.mc.thePlayer);
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, 0, 0, this.mc.thePlayer);
    }
    
    private int getGappleInInv() {
        for (int i = 0; i < 35; ++i) {
            if (this.mc.thePlayer.inventory.mainInventory[i] != null) {
                final ItemStack is = this.mc.thePlayer.inventory.mainInventory[i];
                final Item item = is.getItem();
                if (item instanceof ItemAppleGold) {
                    return i;
                }
            }
        }
        return -1;
    }
}
