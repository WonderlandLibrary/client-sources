package net.minecraft.client.triton.utils.minecraft;

import java.net.UnknownHostException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.triton.management.event.EventManager;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.Render2DEvent;
import net.minecraft.client.triton.utils.Timer;
import net.minecraft.entity.player.EntityPlayer;

public class NetworkUtils
{
  static OldServerPinger pinger = new OldServerPinger();
  private Minecraft mc = Minecraft.getMinecraft();
  private Timer timer = new Timer();
  private static long ping;
  private long lastTime;
  private int prevDebugFPS;
  public long updatedPing;
  
  public NetworkUtils()
  {
    EventManager.register(this);
    PingThread pingThread = new PingThread();
    pingThread.start();
  }
  
  public static long getPing()
  {
    return ping;
  }
  
  public int getPlayerPing(String name)
  {
    EntityPlayer player = this.mc.theWorld.getPlayerEntityByName(name);
    if ((player instanceof EntityOtherPlayerMP)) {
      return ((EntityOtherPlayerMP)player).field_175157_a.getResponseTime();
    }
    return 0;
  }
  
  @EventTarget
  private void on2DRender(Render2DEvent event)
  {
    if (Minecraft.debugFPS != this.prevDebugFPS)
    {
      this.prevDebugFPS = Minecraft.debugFPS;
      this.ping = this.updatedPing;
    }
  }
}

