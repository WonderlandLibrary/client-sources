package net.SliceClient.modules;

import java.util.Random;
import java.util.UUID;
import net.SliceClient.Slice;
import net.SliceClient.Utils.TimeHelper;
import net.SliceClient.module.Category;
import net.SliceClient.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Spammer
  extends Module
{
  private String[] spammer = {
    "@a " + Slice.ClientName + "B" + Slice.v + " | by quizexh | YT: quizexh | " };
  
  Random r = new Random();
  
  public Spammer()
  {
    super("Spammer", Category.MISC, 10066380);
    
    if ((Minecraft.thePlayer == null) || (Minecraft.theWorld == null)) {}
  }
  


  public String getMessage()
  {
    return spammer[r.nextInt(spammer.length)] + UUID.randomUUID().toString().toLowerCase().substring(1, 6);
  }
  
  TimeHelper delay = new TimeHelper();
  
  public void onUpdate()
  {
    if (!getState())
      return;
    if (!delay.isDelayComplete(3500L)) {
      return;
    }
    
    Minecraft.thePlayer.sendChatMessage(getMessage());
    delay.setLastMS(System.currentTimeMillis());
  }
}
