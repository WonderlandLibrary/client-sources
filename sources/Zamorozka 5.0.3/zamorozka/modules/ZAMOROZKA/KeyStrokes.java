package zamorozka.modules.ZAMOROZKA;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.ui.RenderingTools;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class KeyStrokes extends Module {

	public KeyStrokes() {
		super("KeyStrokes", 0, Category.Zamorozka);
	}

	int lastA = 0;
	int lastW = 0;
	int lastS = 0;
	int lastD = 0;
	double lastX = 0;
	double lastZ = 0;

	@EventTarget
	public void onRender(EventRender2D event) {
		CFontRenderer ufr = Fonts.comfortaa20;
		boolean A = mc.gameSettings.keyBindLeft.pressed;
		boolean W = mc.gameSettings.keyBindForward.pressed;
		boolean S = mc.gameSettings.keyBindBack.pressed;
		boolean D = mc.gameSettings.keyBindRight.pressed;
		int alphaA = A ? 255 : 0;
		int alphaW = W ? 255 : 0;
		int alphaS = S ? 255 : 0;
		int alphaD = D ? 255 : 0;

		if (lastA != alphaA) {
			float diff = alphaA - lastA;
			lastA += diff / 30;
		}
		if (lastW != alphaW) {
			float diff = alphaW - lastW;
			lastW += diff / 30;
		}
		if (lastS != alphaS) {
			float diff = alphaS - lastS;
			lastS += diff / 30;
		}
		if (lastD != alphaD) {
			float diff = alphaD - lastD;
			lastD += diff / 30;
		}

        Gui.drawRect(5, 50 - 1, 25, 70 - 1, new Color(lastA, lastA, lastA, 150).getRGB());
        ufr.drawCenteredStringWithShadow("A", 15,54 - 1,new Color(flop(lastA, 255), flop(lastA, 255), flop(lastA, 255), 255).getRGB());

        Gui.drawRect(5 + 22, 27, 27 + 20, 47, new Color(lastW, lastW, lastW, 150).getRGB());
        ufr.drawCenteredStringWithShadow("W", 37,32,new Color(flop(lastW, 255), flop(lastW, 255), flop(lastW, 255), 255).getRGB());

        Gui.drawRect(5 + 22, 25 + 24, 27 + 20, 45 + 24, new Color(lastS, lastS, lastS, 150).getRGB());
        ufr.drawCenteredStringWithShadow("S", 37,54,new Color(flop(lastS, 255), flop(lastS, 255), flop(lastS, 255), 255).getRGB());

        Gui.drawRect(5 + 22 + 22, 25 + 24, 27 + 20 + 22, 45 + 24, new Color(lastD, lastD, lastD, 150).getRGB());
        ufr.drawCenteredStringWithShadow("D", 37 + 22,54,new Color(flop(lastD, 255), flop(lastD, 255), flop(lastD, 255), 255).getRGB());

        Gui.drawRect(5, 75, 5 + 64, 75 + 64, new Color(0, 0, 0, 150).getRGB());

		float diffX = mc.player.rotationYaw - mc.player.lastReportedYaw;
		float diffY = mc.player.rotationPitch - mc.player.lastReportedPitch;

		Gui.drawRect(5, 74 + ((75 + 64) - 75) / 2, 5 + 64, 75 + ((75 + 64) - 74) / 2,
				new Color(255, 255, 255, 150).getRGB());
		Gui.drawRect(3 + ((5 + 64) / 2), 75, 4 + ((5 + 64) / 2), 75 + 64, new Color(255, 255, 255, 150).getRGB());
		RenderingTools.drawCircle(4 + (int) diffX / 3 + ((5 + 64) / 2), 74 + (int) diffY / 3 + ((75 + 64) - 75) / 2, 7,
				6, new Color(255, 255, 255, 200).getRGB());

		// motion

		Gui.drawRect(5, 75 + 66, 5 + 64, 75 + 64 + 66, new Color(0, 0, 0, 150).getRGB());
		Gui.drawRect(5, 74 + 66 + ((75 + 64) - 75) / 2, 5 + 64, 75 + 66 + ((75 + 64) - 74) / 2,
				new Color(255, 255, 255, 150).getRGB());
		Gui.drawRect(3 + ((5 + 64) / 2), 75 + 66, 4 + ((5 + 64) / 2), 75 + 64 + 66,
				new Color(255, 255, 255, 150).getRGB());
		double prevX = mc.player.posX - mc.player.prevPosX;
		double prevZ = mc.player.posZ - mc.player.prevPosZ;
		double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
		double motionX = mc.player.moveStrafing * (lastDist * 200);
		double motionZ = mc.player.moveForward * (lastDist * 200);
		lastX += (motionX - lastX) / 4;
		lastZ += (motionZ - lastZ) / 4;
		RenderingTools.drawCircle(4 + (int) lastX / 3 + ((5 + 64) / 2),
				74 + (int) lastZ / 3 + 66 + ((75 + 64) - 74) / 2, 7, 50, new Color(255, 255, 255, 200).getRGB());

		double currSpeed = lastDist * 15.3571428571;
		String speed = String.format("BPS: %.2f", currSpeed);
		Zamorozka.FONT_MANAGER.arraylist4.drawStringWithShadow(speed, 6, 75 + 69 + 66, 0xFFFFFFFF);
	}

	public int flop(int a, int b) {
		return b - a;
	}
}