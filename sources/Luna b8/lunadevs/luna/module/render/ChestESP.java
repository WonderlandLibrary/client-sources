package lunadevs.luna.module.render;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.tileentity.TileEntityChest;

public class ChestESP extends Module{

	public static boolean active;
	
	public ChestESP() {
		super("ChestESP", Keyboard.KEY_NONE, Category.RENDER, false);
	}
	
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		super.onUpdate();
	}
	
	@Override
	public void onEnable() {
		active = true;
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		active = false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
