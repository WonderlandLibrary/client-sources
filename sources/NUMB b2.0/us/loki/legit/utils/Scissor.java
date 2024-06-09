package us.loki.legit.utils;

import static org.lwjgl.opengl.GL11.glScissor;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;

public class Scissor {

	public static void enableScissoring() {
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
	}
	

	public static void doGlScissor(int x, int y, int width, int height) {
		if (x == width || y == height) {
			return;
		}
		Minecraft mc = Minecraft.getMinecraft();
		int scaleFactor = 1;
		int k = mc.gameSettings.guiScale;

		while (scaleFactor < k && mc.displayWidth / (scaleFactor + 1) >= 320
				&& mc.displayHeight / (scaleFactor + 1) >= 240) {
			++scaleFactor;
		}
		glScissor(x * scaleFactor, mc.displayHeight - (y + height) * scaleFactor, width * scaleFactor,
				height * scaleFactor);

	}

	public static void disableScissoring() {
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
}
