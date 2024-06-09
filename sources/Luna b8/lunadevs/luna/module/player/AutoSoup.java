package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;

public class AutoSoup extends Module{

	public AutoSoup() {
		super("AutoSoup", Keyboard.KEY_NONE, Category.PLAYER, false);
	}
	
	
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if (((mc.currentScreen instanceof GuiContainer)) && (!(mc.currentScreen instanceof GuiInventory))) {
		      return;
		    }
		    EntityPlayerSP player = mc.thePlayer;
		    if (player.getHealth() >= 12.0F) {
		      return;
		    }
		    int soupInInventory = findSoup(9, 36);
		    int soupInHotbar = findSoup(36, 45);
		    if ((soupInInventory == -1) && (soupInHotbar == -1)) {
		      return;
		    }
		    Container inventoryContainer = player.inventoryContainer;
		    PlayerControllerMP playerController = mc.playerController;
		    for (int i = 9; i < 45; i++)
		    {
		      ItemStack stack = inventoryContainer.getSlot(i).getStack();
		      if ((stack != null) && (stack.getItem() == Items.bowl))
		      {
		        playerController.windowClick(0, i, 0, 0, player);
		        playerController.windowClick(0, 18, 0, 0, player);
		      }
		    }
		    if (soupInHotbar != -1)
		    {
		      int oldSlot = player.inventory.currentItem;
		      NetHandlerPlayClient sendQueue = player.sendQueue;
		      sendQueue.addToSendQueue(new C09PacketHeldItemChange(soupInHotbar - 36));
		      playerController.updateController();
		      sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), -1, inventoryContainer.getSlot(soupInHotbar).getStack(), 0.0F, 0.0F, 0.0F));
		      sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
		    }
		    else
		    {
		      playerController.windowClick(0, soupInInventory, 0, 1, player);
		    }
		super.onUpdate();
	}
	
	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	@Override
	public String getValue() {
		return null;
	}
	  private int findSoup(int startSlot, int endSlot)
	  {
	    for (int i = startSlot; i < endSlot; i++)
	    {
	      ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
	      if ((stack != null) && (stack.getItem() == Items.mushroom_stew)) {
	        return i;
	      }
	    }
	    return -1;
	  }
}
