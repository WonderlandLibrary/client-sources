package zamorozka.ui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public final class PlayerController
{
	public static Minecraft mc = Minecraft.getMinecraft();
  private static PlayerControllerMP getPlayerController()
  {
    return Minecraft.getMinecraft().playerController;
  }
  
  public static ItemStack windowClick_PICKUP(int slot)
  {
    return getPlayerController().windowClick(0, slot, 0, ClickType.PICKUP, 
      mc.player);
  }
  
  public static ItemStack windowClick_QUICK_MOVE(int slot)
  {
    return getPlayerController().windowClick(0, slot, 0, 
      ClickType.QUICK_MOVE, mc.player);
  }
  
  public static void processRightClick()
  {
    getPlayerController().processRightClick(mc.player, 
     mc.world, EnumHand.MAIN_HAND);
  }
  
  public static void attackAndSwing(Entity entity)
  {
    getPlayerController().attackEntity(mc.player, entity);
   
  }
}
