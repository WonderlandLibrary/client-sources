package net.SliceClient.commands;

import net.SliceClient.commandbase.Command;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.C01PacketChatMessage;

public class Say extends Command
{
  public Say()
  {
    super("say", "say <text>");
  }
  
  public void execute(String[] args)
  {
    if (args.length < 1) {
      net.SliceClient.Utils.Util.addChatMessage(net.SliceClient.Slice.prefix + ".Say <Text>");
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (String s : args) {
      sb.append(s).append(" ");
    }
    String text = sb.toString().trim();
    Minecraft.getMinecraft();thePlayersendQueue.addToSendQueue(new C01PacketChatMessage(text));
  }
}
