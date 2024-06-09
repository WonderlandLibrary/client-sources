package us.loki.legit.modules.impl.Render;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import us.loki.legit.modules.*;

public class HitAnimation extends Module {
	public HitAnimation() {
		super("HitAnimation", "HitAnimation", Keyboard.KEY_NONE, Category.MODS);
	}

	@Override
	public void onEnable() {
		EventManager.register(this);
		super.onEnable();
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
		super.onDisable();
	}
}
