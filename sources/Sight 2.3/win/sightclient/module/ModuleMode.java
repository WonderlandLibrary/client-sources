package win.sightclient.module;

import net.minecraft.client.Minecraft;
import win.sightclient.event.Event;

public class ModuleMode {

	protected Module parent;
	protected static Minecraft mc = Minecraft.getMinecraft();
	
	public ModuleMode(Module parent) {
		this.parent = parent;
	}
	 
	public void onEvent(Event e) {}
	
	public void onEnable() {}
	
	public void onDisable() {}
}
