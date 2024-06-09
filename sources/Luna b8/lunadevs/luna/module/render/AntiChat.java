package lunadevs.luna.module.render;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.tileentity.TileEntityChest;

public class AntiChat extends Module{

	
	public AntiChat() {
		super("Chat+", Keyboard.KEY_NONE, Category.RENDER, false);
	}
	
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		mc.gameSettings.chatScale = 1;
		mc.gameSettings.chatWidth = 4;
		mc.gameSettings.chatHeightFocused = 6;
		super.onUpdate();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		mc.gameSettings.chatScale = 1;
		mc.gameSettings.chatWidth = 1.2f;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
