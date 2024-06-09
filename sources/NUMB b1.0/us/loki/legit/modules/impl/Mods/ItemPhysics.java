package us.loki.legit.modules.impl.Mods;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;

import us.loki.legit.modules.*;

public class ItemPhysics extends Module {
	public ItemPhysics() {
		super("ItemPhysics", "ItemPhysics", Keyboard.KEY_NONE, Category.MODS);
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
