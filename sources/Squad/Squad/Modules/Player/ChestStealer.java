package Squad.Modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.Utils.Wrapper;
import Squad.base.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.client.C0DPacketCloseWindow;


public class ChestStealer extends Module {

	public ChestStealer() {
		super("ChestStealer", Keyboard.KEY_L, 0xFFFFFF, Category.Player);
	}


	TimeHelper timer;
	

	@EventTarget
	public void onTick(EventUpdate e) {
		if (Wrapper.getMC().currentScreen instanceof GuiChest) {
			EntityPlayerSP player = Wrapper.getPlayer();
			Container chest = player.openContainer;
			boolean hasSpace = false;
			for (int i = chest.inventorySlots.size() - 36; i < chest.inventorySlots.size(); ++i) {
				Slot slot = chest.getSlot(i);
				if (slot != null && slot.getHasStack())
					continue;
				hasSpace = true;
				break;
			}
			if (!hasSpace) {
				return;
			}
				Slot slot;
				int i2;
				boolean item_found = false;
				for (i2 = 0; i2 < chest.inventorySlots.size() - 36; ++i2) {
					slot = chest.getSlot(i2);
					if (!slot.getHasStack() || slot.getStack() == null)
						continue;
					Wrapper.getPlayerController().windowClick(chest.windowId, i2, 0, 1, player);
					item_found = true;
					break;
				}
				if (!item_found) {
					Wrapper.getMC().displayGuiScreen(null);
					player.sendQueue.addToSendQueue(new C0DPacketCloseWindow(chest.windowId));
				}
				hasSpace = false;
				for (i2 = chest.inventorySlots.size() - 36; i2 < chest.inventorySlots.size(); ++i2) {
					slot = chest.getSlot(i2);
					if (slot != null && slot.getHasStack())
						continue;
					hasSpace = true;
					break;
				}
				if (hasSpace)
				return;
		}
	}
}
	

