package Focus.Beta.IMPL.Module.impl.render;

import java.awt.Color;
import java.util.ArrayList;

import Focus.Beta.API.GUI.notifications.NotificationRenderer;
import Focus.Beta.Client;
import Focus.Beta.IMPL.Module.impl.combat.TeleportAura;
import Focus.Beta.UTILS.render.RenderUtil2;
import Focus.Beta.UTILS.world.MovementUtil;
import Focus.Beta.UTILS.world.Timer;
import com.mojang.realmsclient.gui.ChatFormatting;

import Focus.Beta.API.EventHandler;
import Focus.Beta.API.events.render.EventRender2D;
import Focus.Beta.IMPL.Module.Module;
import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.font.CFontRenderer;
import Focus.Beta.IMPL.font.FontLoaders;
import Focus.Beta.IMPL.managers.ModuleManager;
import Focus.Beta.IMPL.set.Mode;
import Focus.Beta.IMPL.set.Option;
import Focus.Beta.UTILS.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class HUD extends Module {

	public static Mode<Enum> fontMode = new Mode("Font", "Font", FontMode.values(), FontMode.Focus);
	public static String moduleName;
	public static Option<Boolean> sorting = new Option<>("Sorting", "Sorting", true);
	public static Option<Boolean> bg = new Option<>("BackGround", "BackGround", false);
	public static Mode<Enum> arrayMode = new Mode("Color", "Color", ArrayMode.values(), ArrayMode.Focus);
	public static Mode<Enum> WaterMarkmode = new Mode("WaterMark", "WaterMark", WaterMarkMode.values(), WaterMarkMode.Focus);
	public static int color;
	int flykickint = 0;
	public static CFontRenderer font = null;
	Timer timer = new Timer();
	private int waterMarkColor;

	public HUD() {
		super("Hud", new String[0], Type.RENDER, "No");

		this.setEnabled(true);
		addValues(sorting,fontMode, arrayMode, WaterMarkmode);
	}


	@EventHandler
	public void onHUD(EventRender2D event) {

		ScaledResolution sr = new ScaledResolution(mc);
		int width = sr.getScaledWidth();
		final boolean bottom = false;
		int height = sr.getScaledHeight();
		if (mc.gameSettings.showDebugInfo) {
			return;
		}

		if (ModuleManager.getModuleByName("Fly").isEnabled()) {
			RenderUtil.drawRect(sr.getScaledWidth() - 100, sr.getScaledHeight() - 19, sr.getScaledWidth() - 5, sr.getScaledHeight() - 5, new Color(0, 0, 0, 97).getRGB());
			FontLoaders.arial18.drawStringWithShadowNew("Fly Kick", sr.getScaledWidth() - 45, sr.getScaledHeight() - 26.5f, -1);
			RenderUtil.drawRect(sr.getScaledWidth() - 5 - flykickint, sr.getScaledHeight() - 19, sr.getScaledWidth() - 5, sr.getScaledHeight() - 5, color);
			if (!MovementUtil.isOnGround(0.48f) && timer.hasElapsed(50, true) && !(flykickint == 98)) {
				flykickint++;
			} else if (timer.hasElapsed(100, true)) {
				flykickint = 0;
			}
		} else {
			flykickint = 0;
		}
		String name;
		switch (fontMode.getModeAsString()) {
			case "Focus":
				font = FontLoaders.arial18;
				break;
		}


		ArrayList<Module> sorted = new ArrayList<Module>();
		for (Module m : Client.instance.getModuleManager().getModules()) {
			if (!m.isEnabled() || m.wasRemoved())
				continue;
			sorted.add(m);
		}


		if(sorting.getValue()) {
			if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
				sorted.sort((o1, o2) -> mc.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s - %s", o2.getName(), o2.getSuffix())) - mc.fontRendererObj.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s - %s", o1.getName(), o1.getSuffix())));

			} else {
				CFontRenderer finalFont = FontLoaders.arial18;
				sorted.sort((o1, o2) -> finalFont.getStringWidth(o2.getSuffix().isEmpty() ? o2.getName() : String.format("%s - %s", o2.getName(), o2.getSuffix())) - finalFont.getStringWidth(o1.getSuffix().isEmpty() ? o1.getName() : String.format("%s - %s", o1.getName(), o1.getSuffix())));
			}
		}

		//Arraylist
		int y = 1;
		int count = 0;
		for (Module m : sorted) {
			float aOffset = (float) (System.currentTimeMillis() % (int) 3200.0f) + (count * 500 / 2);
			switch (arrayMode.getModeAsString()) {
				case "Focus":
					color = color(y, 200);
					break;
				case "FadeGreen":
					color = colorG(y, 200);
					break;
				case "FadeRed":
					color = colorR(y, 200);
					break;
				case "Test":
					color = -1;
					break;
			}

			name = m.getSuffix().isEmpty() ? m.getName() : String.format("%s - %s", m.getName(), m.getSuffix());
			double bottemLines = 0;

			ArrayList<Module> enabledModules = new ArrayList<Module>();
			for (Module e : Client.instance.getModuleManager().getModules()) {
				if (e.isEnabled()) {
					enabledModules.add(e);
				}
			}

			try {

				if (enabledModules.indexOf(m) != enabledModules.size() - 1) {
					bottemLines = (mc.fontRendererObj.getStringWidth(enabledModules.get(enabledModules.indexOf(m)).name)) - (mc.fontRendererObj.getStringWidth(name));
				} else {
					bottemLines = (mc.fontRendererObj.getStringWidth(name)) + 10;
				}

			} catch (IndexOutOfBoundsException e) {
				// TODO: handle exception
			}
			float x = 0;
			if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
				x = RenderUtil.width() - mc.fontRendererObj.getStringWidth(name);
			} else {
				x = RenderUtil.width() - font.getStringWidth(name);
			}


			if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {



				if(bg.getValue())
					Gui.drawRect(sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(name) - 6f, y + 1.2f, sr.getScaledWidth(), 6 + mc.fontRendererObj.FONT_HEIGHT + y - 3.8f, new Color(0, 0, 0, 60).getRGB());
				Gui.drawRect(sr.getScaledWidth() - 2f, y + 1f, sr.getScaledWidth(), 6 + mc.fontRendererObj.FONT_HEIGHT + y - 3.5f, color);

				mc.fontRendererObj.drawStringWithShadow(name, x - 4, y + 2, color);
				y += 1;
			} else {
				if(bg.getValue())
					Gui.drawRect(sr.getScaledWidth() - font.getStringWidth(name) - 6f, y + .5f,sr.getScaledWidth(), 6 + font.getHeight() + y - 3.5f, new Color(0, 0, 0, 60).getRGB());
				Gui.drawRect(sr.getScaledWidth() - 2.5f, y, sr.getScaledWidth(), 6f + font.getHeight() + y - 3.5f, color);
				FontLoaders.arial18.drawStringWithShadowNew(name, x - 4, y + 2, color);
			}
			y += 9;
			count++;


		}


		int count2 = 0;
		switch (arrayMode.getModeAsString()) {
			case "Focus":
				waterMarkColor = color(count2, 200);
				break;
			case "FadeGreen":
				waterMarkColor = colorG(count2, 200);
				break;
			case "FadeRed":
				waterMarkColor = colorR(count2, 200);
				break;
			case "Astolfo":
				waterMarkColor = -1;
				break;
		}
		String focusString = "";
		String descString = Client.ClientDesc;
		if (!mc.isSingleplayer()) {
			focusString = "F" + ChatFormatting.WHITE + "ocus Beta | " + mc.thePlayer.getName() + " | " + mc.getCurrentServerData().serverIP.toLowerCase().toString();
		} else {

			focusString = "F" + ChatFormatting.WHITE + "ocus | " + mc.thePlayer.getName() + " | SinglePlayer";
		}
		if (WaterMarkmode.getModeAsString().equalsIgnoreCase("Focus")) {
			if (fontMode.getModeAsString().equalsIgnoreCase("Minecraft")) {
				RenderUtil.drawRoundedRect(10, 10, mc.fontRendererObj.getStringWidth(focusString) + 17, 30, 30, new Color(0, 0, 0, 70).getRGB());
				GlStateManager.color(1, 1, 1);
				RenderUtil.drawRoundedRect(10, 10, mc.fontRendererObj.getStringWidth(focusString) + 18, 13.5F, 10, color);

				mc.fontRendererObj.drawStringWithShadow(focusString, 12F, 17.0F, color);
				count2++;
			} else {

				RenderUtil.drawRoundedRect(10, 10, FontLoaders.arial20.getStringWidth(focusString) + 17, 30, 30, new Color(0, 0, 0, 70).getRGB());

				GlStateManager.color(1, 1, 1);
				FontLoaders.arial20.drawStringWithShadow(focusString, 12F, 17.0F, waterMarkColor);
				RenderUtil.drawRoundedRect(10, 10, FontLoaders.arial20.getStringWidth(focusString) + 17, 13.5F, 10, color);
				count2++;
			}
		} else {
			focusString = ChatFormatting.WHITE + "FocusBeta" + ChatFormatting.RESET + "" + ChatFormatting.WHITE + " | " + mc.thePlayer.getName() + " | " + (mc.isSingleplayer() ? "SinglePlayer" : mc.getCurrentServerData().serverIP.toString() + " | " + Client.instance.edition);

			final float width2 = (float)(FontLoaders.arial20.getStringWidth(focusString) - 4.0);	final int height2 = 20;
			final int posX2 = 2;
			final int posY1 = 2;
			Gui.drawRect(posX2, posY1, posX2 + width2 + 2.0f, posY1 + height2, new Color(5, 5, 5, 255).getRGB());
			RenderUtil.drawBorderedRect(posX2 + 0.5, posY1 + 0.5, posX2 + width2 + 1.5, posY1 + height2 - 0.5, 0.5, new Color(40, 40, 40, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
			RenderUtil.drawBorderedRect(posX2 + 2, posY1 + 2, posX2 + width2, posY1 + height2 - 2, 0.5, new Color(22, 22, 22, 255).getRGB(), new Color(60, 60, 60, 255).getRGB(), true);
			Gui.drawRect(posX2 + 2.5, posY1 + 2.5, posX2 + width2 - 0.5, posY1 + 4.5, new Color(9, 9, 9, 255).getRGB());
			RenderUtil.drawGradientSideways(4.0, posY1 + 3, 4.0f + width2 / 3.0f, posY1 + 4, new Color(81, 149, 219, 255).getRGB(), new Color(180, 49, 218, 255).getRGB());
			RenderUtil.drawGradientSideways(4.0f + width2 / 3.0f, posY1 + 3, 4.0f + width2 / 3.0f * 2.0f, posY1 + 4, new Color(180, 49, 218, 255).getRGB(), new Color(236, 93, 128, 255).getRGB());
			RenderUtil.drawGradientSideways(4.0f + width2 / 3.0f * 2.0f, posY1 + 3, width2 / 3.0f * 3.0f + 1.0f, posY1 + 4, new Color(236, 93, 128, 255).getRGB(), new Color(167, 171, 90, 255).getRGB());
			FontLoaders.arial18.drawString(focusString, 7.5F, 10.0f, Color.white.getRGB());
		}
		}





	public static int darker(int color, float factor) {
		int r = (int) ((color >> 16 & 0xFF) * factor);
		int g = (int) ((color >> 8 & 0xFF) * factor);
		int b = (int) ((color & 0xFF) * factor);
		int a = color >> 24 & 0xFF;

		return (r & 0xFF) << 16 | (
				g & 0xFF) << 8 |
				b & 0xFF | (
				a & 0xFF) << 24;
	}

	public static void drawExhiRect(float left, float top, float right, float bottom) {
		RenderUtil.drawGradientRect(left - 3.5, top - 3.5, right + 105.5f, bottom + 42.4f, new Color(10, 10, 10, 255).getRGB(), new Color(10, 10, 10, 255).getRGB());
		//.prepareScissorBox ( left, top, right + 140.0f,bottom);
		RenderUtil.drawGradientRect(left - 3, top - 3.2, right + 104.8f, bottom + 41.8f, new Color(40, 40, 40, 255).getRGB(), new Color(40, 40, 40, 255).getRGB());
		RenderUtil.drawGradientRect(left - 1.4, top - 1.5, right + 103.5f, bottom + 40.5f, new Color(74, 74, 74, 255).getRGB(), new Color(74, 74, 74, 255).getRGB());
		RenderUtil.drawGradientRect(left - 1, top - 1, right + 103.0f, bottom + 40.0f, new Color(32, 32, 32, 255).getRGB(), new Color(10, 10, 10, 255).getRGB());
	}

	private static float getOffset() {
		return (System.currentTimeMillis() % 2000) / 1000f;
	}

	public static int colore(int index, int count) {
		float[] hsb = new float[3];
		final Color clr = new Color(255, 255, 255);
		Color.RGBtoHSB(clr.getRed(), clr.getGreen(), clr.getBlue(), hsb);
		float brightness = Math.abs(((getOffset() + (index / (float) count) * 4) % 2) - 1);
		brightness = 0.4f + (0.4f * brightness);

		hsb[2] = brightness % 1f;
		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}

	public enum FontMode {
		Focus, Minecraft
	}

	public static void drawModalRectWithCustomSizedTexture(float x, float y, double u, double v, int width, double height, float textureWidth, double textureHeight) {
		float f = 1.0F / textureWidth;
		float f1 = (float) (1.0F / textureHeight);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
		worldrenderer.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
		worldrenderer.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
		worldrenderer.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
		tessellator.draw();
	}

	public int color(int index, int count) {
		float[] hsb = new float[3];
		Color.RGBtoHSB(2, 53, 120, hsb);
		float brightness = Math.abs(((getOffset() + (index / (float) count) * 4) % 2) - 1);
		brightness = 0.4f + (0.4f * brightness);

		hsb[2] = brightness % 1f;
		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}


	public int colorG(int index, int count) {
		float[] hsb = new float[3];
		Color.RGBtoHSB(
				61, 235, 84
				, hsb);
		float brightness = Math.abs(((getOffset() + (index / (float) count) * 4) % 2) - 1);
		brightness = 0.4f + (0.4f * brightness);

		hsb[2] = brightness % 1f;
		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}

	public int colorR(int index, int count) {
		float[] hsb = new float[3];
		Color.RGBtoHSB(237, 53, 40, hsb);
		float brightness = Math.abs(((getOffset() + (index / (float) count) * 4) % 2) - 1);
		brightness = 0.4f + (0.4f * brightness);

		hsb[2] = brightness % 1f;
		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}

	public enum ArrayMode {
		Focus, Test, FadeGreen, FadeRed
	}

	public enum BorderMode {
		NONE, Right, Left, WrapperLeft
	}

	public static Color getGradientOffset(Color color1, Color color2, double offset) {
		if (offset > 1) {
			double left = offset % 1;
			int off = (int) offset;
			offset = off % 2 == 0 ? left : 1 - left;

		}
		double inverse_percent = 1 - offset;
		int redPart = (int) (color1.getRed() * inverse_percent + color2.getRed() * offset);
		int greenPart = (int) (color1.getGreen() * inverse_percent + color2.getGreen() * offset);
		int bluePart = (int) (color1.getBlue() * inverse_percent + color2.getBlue() * offset);
		return new Color(redPart, greenPart, bluePart);
	}

	private enum WaterMarkMode {
		Focus, Skeet
	}
}
