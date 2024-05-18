package mods.togglesprint.me.jannik.module.modules.player;

import java.util.List;
import mods.togglesprint.com.darkmagician6.eventapi.EventTarget;
import mods.togglesprint.me.jannik.events.EventUpdate;
import mods.togglesprint.me.jannik.module.Category;
import mods.togglesprint.me.jannik.module.Module;
import mods.togglesprint.me.jannik.utils.TimeHelper;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.values.Values;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ChestStealer
  extends Module
{
  TimeHelper time = new TimeHelper();
  
  public ChestStealer()
  {
    super("ChestStealer", Category.PLAYER);
  }
  
  @EventTarget
  public void onUpdate(EventUpdate event)
  {
    Container chest = mc.thePlayer.openContainer;
    if ((chest != null) && ((chest instanceof ContainerChest)))
    {
      ContainerChest container = (ContainerChest)chest;
      int i = 0;
      while (i < container.getLowerChestInventory().getSizeInventory())
      {
        if ((container.getLowerChestInventory().getStackInSlot(i) != null) && (this.time.hasReached(Values.cheststealer_delay.getFloatValue())))
        {
          mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
          this.time.reset();
        }
        i++;
      }
      if ((isContainerEmpty(chest)) && (Values.cheststealer_closechest.getBooleanValue())) {
        mc.thePlayer.closeScreen();
      }
    }
  }
  
  private boolean isContainerEmpty(Container container)
  {
    boolean temp = true;
    int i = 0;
    for (int slotAmount = container.inventorySlots.size() == 90 ? 54 : 27; i < slotAmount; i++) {
      if (container.getSlot(i).getHasStack()) {
        temp = false;
      }
    }
    return temp;
  }
}
