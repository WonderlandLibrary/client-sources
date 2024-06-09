package lunadevs.luna.commands;

import lunadevs.luna.command.Command;
import net.minecraft.client.Minecraft;

public class Hclip extends Command{

	@Override
	public String getAlias() {
		return "hclip";
	}

	@Override
	public String getDescription() {
		return "TP forward and backwards";
	}

	@Override
	public String getSyntax() {
		return "-hclip <blocks>";
	}

	@Override
	public void onCommand(String command, String[] args) throws Exception {
		  double yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
          int increment = Integer.parseInt(args[0].trim());
          mc.thePlayer.setPosition(
          mc.thePlayer.posX + 
          Math.sin(Math.toRadians(-yaw)) * increment, 
          mc.thePlayer.posY, 
          mc.thePlayer.posZ + 
          Math.cos(Math.toRadians(-yaw)) * increment);
	}

}
