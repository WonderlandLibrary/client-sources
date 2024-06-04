package yung.purity.module.modules.combat;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventUpdate;
import yung.purity.module.Module;
import yung.purity.utils.TimerUtil;

public class Fastbow extends Module
{
  private TimerUtil timer = new TimerUtil();
  
  public Fastbow() {
    super("FastBow", new String[] { "zoombow", "fastuse" }, yung.purity.module.ModuleType.Combat);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  
  @EventHandler
  private void onUpdate(EventUpdate e)
  {
    if ((e.getType() == 1) && (mc.thePlayer.getCurrentEquippedItem() != null) && ((mc.thePlayer.getHeldItem().getItem() instanceof ItemBow)) && 
      (mc.thePlayer.onGround) && (mc.gameSettings.keyBindUseItem.getIsKeyPressed()))
    {

      mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
      

      for (int i = 0; i < 20; i++)
      {
        if (timer.hasReached(100.0D)) {
          mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - Double.POSITIVE_INFINITY, mc.thePlayer.posZ, mc.thePlayer.onGround));
          timer.reset();
        }
        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.onGround));
      }
      
      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }
  }
}
