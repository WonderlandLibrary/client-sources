/*
 * Decompiled with CFR 0_122.
 */
package winter.module.modules;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import winter.event.EventListener;
import winter.event.events.UpdateEvent;
import winter.module.Module;
import winter.utils.other.Timer;
import winter.utils.value.Value;
import winter.utils.value.types.NumberValue;

public class AutoSoup
extends Module {
    public static Timer timer;
    public static boolean doSoup;
    private NumberValue health;
    private NumberValue delay;

    public AutoSoup() {
        super("AutoSoup", Module.Category.Combat, -5669);
        this.setBind(35);
        this.health = new NumberValue("Base Health", 7.0, 1.0, 20.0, 1.0);
        this.delay = new NumberValue("Delay", 250.0, 250.0, 1000.0, 1.0);
        this.addValue(this.health);
        this.addValue(this.delay);
    }

    @Override
    public void onEnable() {
        timer = new Timer();
    }

    @EventListener
    public void onUpdate(UpdateEvent event) {
        int soupSlot = this.getHealingItemFromInventory();
        this.mode(" " + this.getCount());
        if (event.isPre()) {
            if (timer.hasPassed((float)this.delay.getValue()) && this.mc.thePlayer.getHealth() <= (float)this.getBestHealth() && soupSlot != -1) {
                timer.reset();
                doSoup = true;
            }
        } else if (!event.isPre() && doSoup && soupSlot != -1) {
            doSoup = false;
            this.swap(soupSlot, 5);
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(5));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.DROP_ALL_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.mc.thePlayer.inventory.currentItem));
        }
    }

    public int getCount() {
        int counter = 0;
        int i2 = 9;
        while (i2 < 45) {
            ItemStack is2;
            Item item;
            if (this.mc.thePlayer.inventoryContainer.getSlot(i2).getHasStack() && (item = (is2 = this.mc.thePlayer.inventoryContainer.getSlot(i2).getStack()).getItem()) instanceof ItemSoup) {
                ++counter;
            }
            ++i2;
        }
        return counter;
    }

    private int getHealingItemFromInventory() {
        int itemSlot = -1;
        int i2 = 9;
        while (i2 < 45) {
            ItemStack is2;
            Item item;
            if (this.mc.thePlayer.inventoryContainer.getSlot(i2).getHasStack() && (item = (is2 = this.mc.thePlayer.inventoryContainer.getSlot(i2).getStack()).getItem()) instanceof ItemSoup) {
                itemSlot = i2;
            }
            ++i2;
        }
        return itemSlot;
    }

    private void swap(int slot, int hotbarSlot) {
        this.mc.playerController.windowClick(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarSlot, 2, this.mc.thePlayer);
    }

    public int getBestHealth() {
        int health = (int)this.health.getValue();
        ItemStack[] items = this.mc.thePlayer.getInventory();
        ItemStack boots = items[0];
        ItemStack leggings = items[1];
        ItemStack body = items[2];
        ItemStack helm = items[3];
        if (helm == null) {
            health += 2;
        }
        if (boots == null) {
            health += 2;
        }
        if (body == null) {
            health += 2;
        }
        if (leggings == null) {
            health += 2;
        }
        return health;
    }
}

