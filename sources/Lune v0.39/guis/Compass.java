package me.superskidder.lune.guis;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.render.CompassUtil;
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