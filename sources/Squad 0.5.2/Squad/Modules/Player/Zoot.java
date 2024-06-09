package Squad.Modules.Player;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Category;
import Squad.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Zoot
  extends Module
{
  public Zoot()
  {
    super("Zoot", 0, 136, Category.Other);
  }
  
  @EventTarget
  public void onUpdate(EventUpdate e)
  {
    if (mc.thePlayer.isBurning()) {
      for (int i = 0; i < 3; i++)
      {
    	  mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer());
    	  mc.thePlayer.sendQueue.addToSendQueue(new C00PacketKeepAlive());
      }
    }
  }
}
