package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.Category;
import me.gishreload.yukon.Edictum;
import me.gishreload.yukon.Meanings;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.RenderUtils;
import me.gishreload.yukon.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;


public class AutoArmor extends Module{
	public AutoArmor() {
		super("AutoArmor", 0, Category.PLAYER);
	}
	private Timer time = new Timer();
    private final int[] boots = new int[] { 313, 309, 305, 317, 301 };
    private final int[] chestplate = new int[] { 311, 307, 303, 315, 299 };
    private final int[] helmet = new int[] { 310, 306, 302, 314, 298 };
    private final int[] leggings = new int[] { 312, 308, 304, 316, 300 };
    int j;
    int i;
    
	public void onEnable(){
		Meanings.equip = true;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eAutoArmor \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	public void onDisable() {
		Meanings.equip = false;
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eAutoArmor \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}
	
	@Override
	public void onUpdate()
	{
		if (mc.currentScreen instanceof GuiContainer && !(mc.currentScreen instanceof GuiInventory)) {
            return;
        }
 
        this.time.reset();
        if (
            mc.getPlayer().openContainer != null && mc.getPlayer().openContainer.windowId != 0
                    && mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory)
        ) {
            return;
        }
 
        boolean dropkek = false;
        int item = -1;
        if (mc.getPlayer().inventory.armorInventory[0] == null) {
            for (int id : this.boots) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
 
        if (this.armourIsBetter(0, this.boots)) {
            item = 8;
        }
 
        if (mc.getPlayer().inventory.armorInventory[3] == null) {
            for (int id : this.helmet) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
 
        if (this.armourIsBetter(3, this.helmet)) {
            item = 5;
        }
 
        if (mc.getPlayer().inventory.armorInventory[1] == null) {
            for (int id : this.leggings) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
 
        if (this.armourIsBetter(1, this.leggings)) {
            item = 7;
        }
 
        if (mc.getPlayer().inventory.armorInventory[2] == null) {
            for (int id : this.chestplate) {
                if (this.findItem(id) != -1) {
                    item = this.findItem(id);
                    break;
                }
            }
        }
 
        if (this.armourIsBetter(2, this.chestplate)) {
            item = 6;
        }
 
        boolean hasInvSpace = false;
 
        ItemStack[] arrayOfItemStack;
        for (ItemStack stack : arrayOfItemStack = mc.getPlayer().inventory.mainInventory) {
            if (stack == null) {
                hasInvSpace = true;
                break;
            }
        }

        if(Meanings.equip){
        if (item != -1) {
            mc.playerController.windowClick(
                    0, item, 0, dropkek ? ClickType.THROW : ClickType.QUICK_MOVE, mc.getPlayer()
            );
            this.time.reset();
        }
        }
        }
 
    private int getArrayIndex(int[] array, int input) {
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i] == input) {
                return i;
            }
        }
 
        return -1;
    }
 
    public boolean armourIsBetter(int slot, int[] armourtype) {
        if (mc.getPlayer().inventory.armorInventory[slot] != null) {
            int currentIndex = 0;
            int finalCurrentIndex = -1;
            int invIndex = 0;
            int finalInvIndex = -1;
 
            for (int armour2 : armourtype) {
                if (Item.getIdFromItem(mc.getPlayer().inventory.armorInventory[slot].getItem()) == armour2) {
                    finalCurrentIndex = currentIndex;
                    break;
                }
 
                ++currentIndex;
            }
 
            int j = armourtype.length;
 
            for (this.i = 0; this.i < j; ++this.i) {
                int armour3 = armourtype[this.i];
                if (this.findItem(armour3) != -1) {
                    finalInvIndex = invIndex;
                    break;
                }
 
                ++invIndex;
            }
 
            if (finalInvIndex > -1) {
                return finalInvIndex < finalCurrentIndex;
            }
        }
 
        return false;
    }
 
    private int findItem(int id) {
        for (int index = 9; index < 45; ++index) {
            ItemStack item = mc.getPlayer().inventoryContainer.getSlot(index).getStack();
            if (item != null && Item.getIdFromItem(item.getItem()) == id) {
                return index;
            }
        }
 
        return -1;
    }
}