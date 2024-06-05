package Focus.Beta.IMPL.Module.impl.render;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.world.EventPreUpdate;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;

public class FullBright extends Module{

	public FullBright() {
		super("Brightness", new String[]{ "Brightness", "Bright" }, Type.RENDER, "Allow's to see in night");
	}
	
	@EventHandler
	public void onEnable() {
		   mc.gameSettings.gammaSetting = 100.0F;
	}
	@EventHandler
	public void onDisable() {
		  mc.gameSettings.gammaSetting = 1.0F;
	}
	
	@EventHandler
	public void a(EventPreUpdate e) {
		setSuffix("Gamma");
	}
}
