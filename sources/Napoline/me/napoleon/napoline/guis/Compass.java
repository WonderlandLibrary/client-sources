package me.napoleon.napoline.guis;

import me.napoleon.napoline.events.EventRender2D;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.render.CompassUtil;
import net.minecraft.client.gui.ScaledResolution;

public class Compass extends Mod {
	public Compass() {
		super("Compass", ModCategory.Render,"A Compass");
	}

	@EventTarget
	private void onrender(EventRender2D e) {
		CompassUtil cpass = new CompassUtil(325, 325, 1, 2, true);
		ScaledResolution sc = new ScaledResolution(mc);
		cpass.draw(sc);
	}
}