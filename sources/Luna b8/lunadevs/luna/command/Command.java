package lunadevs.luna.command;

import net.minecraft.client.Minecraft;

public abstract class Command {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public abstract String getAlias();
	public abstract String getDescription();
	public abstract String getSyntax();
	public abstract void onCommand(String command, String[] args) throws Exception;

}