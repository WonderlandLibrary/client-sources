package me.swezedcode.client.module.modules.Fight;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.darkmagician6.eventapi.EventListener;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import net.minecraft.block.BlockAir;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C09PacketHeldItemChange;

public class ClickPearl extends Module {

	private int delay;
	private int oldSlot;

	public ClickPearl() {
		super("ClickPearl", Keyboard.KEY_NONE, 0xFFF216D5, ModCategory.Fight);
		setDisplayName("Click Pearl");
	}

	@EventListener
	public void onPre(EventPreMotionUpdates e) {
		if (((mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword)) && (findHotbarItem(368, 1) != -1)
				&& ((mc.theWorld.getBlockState(mc.objectMouseOver.func_178782_a()).getBlock() instanceof BlockAir)) && Mouse.isButtonDown(2)) {
			mc.gameSettings.keyBindUseItem.pressed = false;
			this.oldSlot = mc.thePlayer.inventory.currentItem;
			mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(findHotbarItem(368, 1)));
			mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
			mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(this.oldSlot));
		}
	}

	public static int findHotbarItem(int itemId, int mode) {
		if (mode == 0) {
			for (int slot = 36; slot <= 44; slot++) {
				ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
				if ((item != null) && (Item.getIdFromItem(item.getItem()) == itemId)) {
					return slot;
				}
			}
		} else if (mode == 1) {
			for (int slot = 36; slot <= 44; slot++) {
				ItemStack item = mc.thePlayer.inventoryContainer.getSlot(slot).getStack();
				if ((item != null) && (Item.getIdFromItem(item.getItem()) == itemId)) {
					return slot - 36;
				}
			}
		}
		return -1;
	}

}