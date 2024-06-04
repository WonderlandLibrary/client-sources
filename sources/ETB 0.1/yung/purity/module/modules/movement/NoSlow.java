package yung.purity.module.modules.movement;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import yung.purity.Client;
import yung.purity.api.EventHandler;
import yung.purity.api.events.EventUpdate;
import yung.purity.management.ModuleManager;
import yung.purity.module.Module;
import yung.purity.module.ModuleType;
import yung.purity.module.modules.combat.Killaura;

public class NoSlow extends Module
{
  public NoSlow()
  {
    super("NoSlow", new String[] { "noslowdown" }, ModuleType.Movement);
    setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB());
  }
  

  @EventHandler
  private void onUpdate(EventUpdate e)
  {
    if ((mc.thePlayer.isBlocking()) && (Client.instance.getModuleManager().getModuleByClass(Killaura.class).isEnabled())) {
      switch (e.getType())
      {
      case 0: 
        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      

      case 1: 
        mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
      }
    }
  }
}
