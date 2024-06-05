package xyz.vergoclient.modules.impl.visual;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventPlayerRender;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.settings.BooleanSetting;

public class Chams extends Module implements OnEventInterface {

	public Chams() {
		super("Chams", Category.VISUAL);
	}

	public BooleanSetting colorCham = new BooleanSetting("Color Cham", false);

	@Override
	public void loadSettings() {
		addSettings(colorCham);
	}

	@Override
	public void onEvent(Event e) {

		if (e instanceof EventPlayerRender) {
			
			if (((EventPlayerRender)e).entity.isUser()) {
				return;
			}
			
			if (e.isPre()) {
				if(colorCham.isEnabled()) {
					GlStateManager.color(0.9f, 0.2f, 0.2f, 1f);
				} else {
					GlStateManager.resetColor();
				}
				GL11.glEnable(32823);
				
				GL11.glPolygonOffset(1.0f, -1099998.0f);
			}
			else if (e.isPost()) {
				if(colorCham.isEnabled()) {
					GlStateManager.color(0.9f, 0.2f, 0.2f, 1f);
				} else {
					GlStateManager.resetColor();
				}
				GL11.glDisable(32823);
				
				GL11.glPolygonOffset(1.0f, 1099998.0f);
			}
			
		}

	}

}
