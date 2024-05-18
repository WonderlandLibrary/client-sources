package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;



public class Highjump
  extends Module
{
  public Highjump()
  {
    super("HighJump", 23, true, ModuleCategory.MOVEMENT);
  }
  
  public void onPacketRecieving(Packet packet)
  {
    if ((ModuleManager.isAntiCheatOn()) && 
      ((packet instanceof S12PacketEntityVelocity))) {
      S12PacketEntityVelocity p = (S12PacketEntityVelocity)packet;
      field_149416_c = ((int)(field_149416_c * 1.9D));
    }
  }
}
