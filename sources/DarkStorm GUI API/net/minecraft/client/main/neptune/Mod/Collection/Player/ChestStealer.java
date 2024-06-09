package net.minecraft.client.main.neptune.Mod.Collection.Player;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.main.neptune.Events.EventPacketTake;
import net.minecraft.client.main.neptune.Events.EventState;
import net.minecraft.client.main.neptune.Events.EventTick;
import net.minecraft.client.main.neptune.Events.EventUpdate;
import net.minecraft.client.main.neptune.Mod.Category;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.NumValue;
import net.minecraft.client.main.neptune.Utils.ClientUtils;
import net.minecraft.client.main.neptune.Utils.TimeMeme;
import net.minecraft.client.main.neptune.memes.Memeager;
import net.minecraft.client.main.neptune.memes.Memetarget;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class ChestStealer extends Mod {
	
		private TimeMeme timer;

		public ChestStealer() {
			super("ChestStealer", Category.HACKS);
			this.setBind(Keyboard.KEY_L);
			this.timer = new TimeMeme();
		}

	    @Memetarget
	    private void onUpdate(final EventUpdate event) {
	        if (event.state.equals(EventState.PRE) && ClientUtils.mc().currentScreen instanceof GuiChest) {
	            final GuiChest guiChest = (GuiChest)ClientUtils.mc().currentScreen;
	            boolean full = true;
	            ItemStack[] mainInventory;
	            for (int length = (mainInventory = ClientUtils.player().inventory.mainInventory).length, i = 0; i < length; ++i) {
	                final ItemStack item = mainInventory[i];
	                if (item == null) {
	                    full = false;
	                    break;
	                }
	            }
	            if(!(ClientUtils.mc().currentScreen instanceof GuiChest)) {
	            	this.timer.reset();
	            }
	            if (!full) {
	                for (int index = 0; index < guiChest.lowerChestInventory.getSizeInventory(); ++index) {
	                    final ItemStack stack = guiChest.lowerChestInventory.getStackInSlot(index);
	                    if (stack != null && this.timer.hasPassed(150)) {
	                        ClientUtils.playerController().windowClick(guiChest.inventorySlots.windowId, index, 0, 1, ClientUtils.player());
	                        this.timer.reset();
	                        break;
	                    }
	                }
	            }
	        }
	    }

}
