package de.tired.api.util.render;

import de.tired.interfaces.IHook;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glDisable;

public enum Scissoring implements IHook {

	SCISSORING;

	public void startScissor() {
		glEnable(GL11.GL_SCISSOR_TEST);
	}



	public void scissorOtherWay(double x, double y, double width, double height) {
		ScaledResolution sr = new ScaledResolution(MC);
		final double scale = sr.getScaleFactor();

		y = sr.getScaledHeight() - y;

		x *= scale;
		y *= scale;
		width *= scale;
		height *= scale;

		GL11.glScissor((int) x, (int) (y - height), (int) width, (int) height);
	}
	public void doScissor(float x, float y, float width, float height) {
		width -= x;
		height -= y;
		ScaledResolution resolution = new ScaledResolution(MC);
		GL11.glScissor((int)x * resolution.getScaleFactor(), (int) MC.displayHeight - (int) y * resolution.getScaleFactor() - (int) height * resolution.getScaleFactor(), (int)width * resolution.getScaleFactor(), (int)height * resolution.getScaleFactor());
	}

	public void disableScissor() {
		glDisable(GL11.GL_SCISSOR_TEST);
	}

}
