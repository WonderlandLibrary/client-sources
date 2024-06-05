package win.sightclient.cmd;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public abstract class Command {

	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public String[] alias;
	
	public Command(String[] alias) {
		this.alias = alias;
	}
	
	public abstract void onCommand(String message);
}
