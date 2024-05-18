package me.hexxed.mercury.commands;

import me.hexxed.mercury.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class Vclip extends me.hexxed.mercury.commandbase.Command
{
  public Vclip()
  {
    super("vclip", "vclip <amount>");
  }
  
  Minecraft mc = Minecraft.getMinecraft();
  private double illegalStance = 1.0D;
  
  public void execute(String[] args)
  {
    if (args.length != 1) {
      Util.addChatMessage(getUsage());
      return;
    }
    Double amount = null;
    try {
      amount = Double.valueOf(Double.parseDouble(args[0]));
    } catch (NumberFormatException e) {
      Util.addChatMessage(getUsage());
      return;
    }
    mc.thePlayer.setLocationAndAngles(mc.thePlayer.posX, amount.doubleValue() + mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
  }
}
