package me.gishreload.yukon.hacks;

import org.lwjgl.input.Keyboard;

import me.gishreload.yukon.*;
import me.gishreload.yukon.module.Module;
import me.gishreload.yukon.utils.Timer;
import me.gishreload.yukon.utils.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.enchantment.EnchantmentProtection.Type;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class ChestStealer extends Module{
    private int cooldown = 1;
	public ChestStealer() {
		super("ChestStealer", 0, Category.PLAYER);
	}
	
	public void onEnable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eChestStealer \u00a72\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
	}

	public void onDisable(){
		Edictum.addChatMessage("\u00a7l\u00a75[\u00a7l\u00a74Edictum\u00a7l\u00a75] \u00a74\u0427\u0438\u0442 \u00a7eChestStealer \u00a74\u0434\u0435\u0430\u043a\u0442\u0438\u0432\u0438\u0440\u043e\u0432\u0430\u043d.");
		super.onDisable();
	}
	
	@Override
	public void onUpdate(){
		if(this.isToggled()){
		        if (this.cooldown > 0) {
		            --this.cooldown;
		            return;
		        }
		        if (mc.currentScreen instanceof GuiChest) {
		            EntityPlayerSP player = mc.thePlayer;
		            Container chest = player.openContainer;
		            boolean hasSpace = false;
		 
		            for (int i = chest.inventorySlots.size() - 36; i < chest.inventorySlots.size(); ++i) {
		                Slot slot = chest.getSlot(i);
		                if (slot == null || !slot.getHasStack()) {
		                    hasSpace = true;
		                    break;
		                }
		            }
		 
		            if (!hasSpace) {
		                return;
		            }
		 
		            while (this.cooldown == 0) {
		                boolean item_found = false;
		 
		                for (int i = 0; i < chest.inventorySlots.size() - 36; ++i) {
		                    Slot slot = chest.getSlot(i);
		                    if (slot.getHasStack() && slot.getStack() != null) {
		                        mc.playerController.windowClick(mc.thePlayer.openContainer.windowId, i, 1, ClickType.QUICK_MOVE,
		                                mc.thePlayer);
		                        this.cooldown = 1;
		                        item_found = true;
		                        break;
		                    }
		                }
		 
		                if (!item_found) {
		                    mc.displayGuiScreen((GuiScreen) null);
		                    player.connection.sendPacket(new CPacketCloseWindow(chest.windowId));
		                    break;
		                }
		 
		                hasSpace = false;
		 
		                for (int i = chest.inventorySlots.size() - 36; i < chest.inventorySlots.size(); ++i) {
		                    Slot slot = chest.getSlot(i);
		                    if (slot == null || !slot.getHasStack()) {
		                        hasSpace = true;
		                        break;
		                    }
		                }
		 
		                if (!hasSpace) {
		                    return;
		                }
		            }
		        }
		    }
	}
}