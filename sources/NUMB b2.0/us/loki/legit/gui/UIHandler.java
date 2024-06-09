package us.loki.legit.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.Minecraft;
import us.loki.legit.modules.*;

import org.lwjgl.opengl.Display;

public class UIHandler {
	
	private static final Minecraft mc = Minecraft.getMinecraft();

	
	public static void render() {
		if (mc.theWorld != null && !mc.gameSettings.hideGUI) {
			for(Module m : ModuleManager.enabledList()) {
				m.render2D();
			}
		}
	}
	
}
