package us.loki.legit.modules.impl.GUI;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.gui.ScaledResolution;
import us.loki.legit.Client;
import us.loki.legit.modules.Category;
import us.loki.legit.modules.Module;

public class HUD extends Module {
	
	public HUD() {
		super("HUD", "HUD", Keyboard.KEY_P, Category.GUI);
	}

}
