package ooo.cpacket.ruby.module.mode;

import net.minecraft.client.Minecraft;
import ooo.cpacket.ruby.module.Module;

public abstract class Mode {
	private Module module;
	private String name;
	
	protected final Minecraft mc = Minecraft.getMinecraft();
	
	public Mode(Module module, String name) {
		this.module = module;
		this.name = name;
	}

	public Module getModule() {
		return this.module;
	}

	public String getName() {
		return this.name;
	}

	public abstract void onEnable();
	
	public abstract void onDisable();
	
}
