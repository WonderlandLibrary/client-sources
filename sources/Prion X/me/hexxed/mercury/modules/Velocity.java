package me.hexxed.mercury.modules;

import me.hexxed.mercury.modulebase.Module;
import me.hexxed.mercury.modulebase.ModuleCategory;
import me.hexxed.mercury.modulebase.ModuleManager;
import me.hexxed.mercury.modulebase.Values;
import me.hexxed.mercury.values.DoubleValue;
import me.hexxed.mercury.values.Value;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity
  extends Module
{
  @Value
  public DoubleValue hor = new DoubleValue("horvelocity", 5.0D, -5.0D, 0.0D);
  
  @Value
  public DoubleValue ver = new DoubleValue("vervelocity", 5.0D, -5.0D, 0.0D);
  
  public Velocity() {
    super("Velocity", 0, true, ModuleCategory.COMBAT);
  }
  
  public void onPacketRecieving(Packet packet)
  {
    if (((packet instanceof S12PacketEntityVelocity)) && ((!ModuleManager.getModByName("HighJump").isEnabled()) || (!ModuleManager.isAntiCheatOn()))) {
      S12PacketEntityVelocity p = (S12PacketEntityVelocity)packet;
      Entity e = mc.getNetHandler().clientWorldController.getEntityByID(p.func_149412_c());
      if (e != mc.thePlayer) {
        return;
      }
      if ((getValueshorvelocity == 0.0D) && (getValuesvervelocity == 0.0D)) {
        setInboundPacketCancelled(true);
        return;
      }
      field_149415_b = ((int)(field_149415_b * getValueshorvelocity));
      field_149416_c = ((int)(field_149416_c * getValuesvervelocity));
      field_149414_d = ((int)(field_149414_d * getValueshorvelocity));
    }
    if ((packet instanceof S27PacketExplosion)) {
      S27PacketExplosion p = (S27PacketExplosion)packet;
      p.func_149149_c();
      field_149152_f = ((float)(field_149152_f * getValueshorvelocity));
      field_149153_g = ((float)(field_149153_g * getValuesvervelocity));
      field_149159_h = ((float)(field_149159_h * getValueshorvelocity));
      return;
    }
  }
}
