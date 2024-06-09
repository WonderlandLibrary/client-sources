package me.hexxed.mercury.commands;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Tp extends me.hexxed.mercury.commandbase.Command
{
  public Tp()
  {
    super("tp", "tp <x> <y> <z>");
  }
  
  private static Minecraft mc = ;
  
  public void execute(String[] args)
  {
    if (args.length != 3) {
      Util.addChatMessage(getUsage());
      return;
    }
    Integer x = null;
    Integer y = null;
    Integer z = null;
    try {
      x = Integer.valueOf(Integer.parseInt(args[0]));
      y = Integer.valueOf(Integer.parseInt(args[1]));
      z = Integer.valueOf(Integer.parseInt(args[2]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    teleport(x.intValue(), y.intValue(), z.intValue());
    if (getMinecraftthePlayer.ridingEntity != null)
    {
      for (int a = 0; a < 20; a++) {
        getMinecraftplayerController.attackEntity(getMinecraftthePlayer, getMinecraftthePlayer.ridingEntity);
      }
    }
    Util.sendInfo("Teleported to §b" + args[0] + ", " + args[1] + ", " + args[2]);
  }
  
  public static void sendFullUpdate(double d, double d1, double d2)
  {
    for (int i = 0; i < 10; i++)
    {
      mcthePlayer.setPosition(d, d1, d2);
      mcthePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition(d, d1, d2, true));
    }
    
    try
    {
      Thread.sleep(3L);
    }
    catch (Exception localException) {}
  }
  









  public static void teleport(double d, final double d1, double d2)
  {
    new Thread(new Runnable()
    {
      public void run()
      {
        Tp.sendFullUpdate(mcthePlayer.posX, 256.0D, mcthePlayer.posZ);
        Tp.sendFullUpdate(val$d, 256.0D, d1);
        Tp.sendFullUpdate(val$d, val$d1, d1);
      }
    })
    







      .start();
  }
}
