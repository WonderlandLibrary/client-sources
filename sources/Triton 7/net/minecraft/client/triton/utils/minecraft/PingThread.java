package net.minecraft.client.triton.utils.minecraft;

import java.net.UnknownHostException;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.triton.utils.ClientUtils;

public class PingThread
extends Thread
{
public PingThread() {
	
}

public void run()
{
  for (;;)
  {
    if (ClientUtils.mc().getCurrentServerData() != null) {
      try
      {
        if (!(ClientUtils.mc().currentScreen instanceof GuiMultiplayer)) {
          NetworkUtils.pinger.ping(ClientUtils.mc().getCurrentServerData());
        }
      }
      catch (UnknownHostException localUnknownHostException) {}
    }
    try
    {
      Thread.sleep(1000L);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
  }
}
}
