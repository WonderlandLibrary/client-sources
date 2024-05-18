package net.SliceClient.modules;

import net.SliceClient.Utils.TimeHelper;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.util.MovementInput;








public class Step
  extends Module
{
  public Step()
  {
    super("Step", Category.PLAYER, 16376546);
  }
  

  public void onDisable()
  {
    thePlayerstepHeight = 0.5F;
  }
  





  TimeHelper time = new TimeHelper();
  
  public void onEnable() {}
  
  public void onUpdate() { if (!getState())
      return;
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {
      return;
    }
    


    if ((thePlayermovementInput != null) && (!thePlayermovementInput.jump) && 
      (thePlayerisCollidedHorizontally) && (thePlayerisCollidedVertically))
    {
      thePlayerstepHeight = 1.5F;
      
      double[] array = { 0.4199999868869782D, 0.75D, 1.0D, 1.159999966621399D, 1.25D, 1.169999957084656D };
      for (int i = 0; i < array.length; i++) {
        Minecraft.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(thePlayerposX, thePlayerposY + array[i], thePlayerposZ, thePlayeronGround));
      }
    }
    else
    {
      thePlayerstepHeight = 0.5F;
    }
  }
}
