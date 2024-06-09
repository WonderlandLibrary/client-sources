
package ca.commencal.ware.module.modules.render;
import ca.commencal.ware.utils.system.Wrapper;
import ca.commencal.ware.module.Module;
import ca.commencal.ware.module.ModuleCategory;


public class NoRain extends Module {
	public NoRain() {
		super("Anti Weather", ModuleCategory.RENDER);
	}


	@Override
	public void onEnable() {
		if (mc.world.isRaining())
			mc.world.setRainStrength(0);
	}

	public void OnDisable() {
		if (mc.world.isRaining())
			return;
	}
}
