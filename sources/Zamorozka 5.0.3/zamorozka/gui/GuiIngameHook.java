package zamorozka.gui;

import java.awt.Color;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.White;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import de.Hero.clickgui.ClickGUI;
import de.Hero.clickgui.Translate;
import de.Hero.settings.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.COMBAT.KillAura;
import zamorozka.modules.EXPLOITS.AuthCrash;
import zamorozka.modules.EXPLOITS.XrauBiposs;
import zamorozka.modules.EXPLOITS.XrayLite;
import zamorozka.modules.HUD.ArreyList;
import zamorozka.modules.HUD.Infodorder;
import zamorozka.modules.HUD.Logo;
import zamorozka.modules.HUD.TextRadar;
import zamorozka.modules.VISUALLY.HUD;
import zamorozka.modules.VISUALLY.NameTags;
import zamorozka.modules.WORLD.Radar;
import zamorozka.modules.WORLD.Tower;
import zamorozka.modules.ZAMOROZKA.KeyStrokes;
import zamorozka.ui.CFont;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.ColorUtilities;
import zamorozka.ui.ColoredString;
import zamorozka.ui.Colors;
import zamorozka.ui.EventRenderGui;
import zamorozka.ui.HSVColor;
import zamorozka.ui.MathUtil;

import zamorozka.ui.MathUtils;
import zamorozka.ui.MinecraftFontRenderer;
import zamorozka.ui.Render2DEvent;
import zamorozka.ui.Render3DEvent;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.Timer2;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.ZItem;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class GuiIngameHook extends GuiScreen {

	private static double animationState;
	private static double animationState1;
	private static double ANIMATION_SPEED = Zamorozka.settingsManager.getSettingByName("SmoothInventory Speed").getValDouble();

	private static Timer2 time1 = new Timer2();

	public static void StartHud() {
		Hud();
	}

	public static Minecraft mc = Minecraft.getMinecraft();
	private static CFontRenderer font = Fonts.comfortaa18;
	private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/icons.png");

	public static void Hud() {

		/*
		 * if(mc.isSingleplayer()) { RPC.RPC("Singleplayer game."); }else {
		 * RPC.RPC("Multiplayer game."); }
		 */
		if (ModuleManager.getModule(AuthCrash.class).getState()) {
			Zamorozka.FONT_MANAGER.Quicksand.drawStringWithShadow("" + ChatFormatting.BLUE + "2b2t queue riper.", 300 + 25f,1 + font.getHeight() + 4f, -1);
		}
		if (ModuleManager.getModule(XrauBiposs.class).getState()) {
			String f = "" + XrauBiposs.all;
			String g = "" + XrauBiposs.done;
			ScaledResolution sr = new ScaledResolution(mc);
			CFontRenderer font = Fonts.comfortaa20;
			int size = 125;
			float xOffset = (sr.getScaledWidth() / 2F) - (size / 2F);
			float yOffset = 5;
			float Y = 0;
			// GlStateManager.enableAlpha();
			RenderingUtils.rectangleBordered(xOffset + 2.0f, yOffset + 1.0f, xOffset + 10 + size + Zamorozka.FONT_MANAGER.arraylist4.getStringWidth(g) + 1.0F, yOffset + (size / 6F) + 3.0f + ((font.getHeight() + 2.2f)), 0.5, Colors.getColor(90),
					Colors.getColor(0));
			RenderingUtils.rectangleBordered(xOffset + 3.0f, yOffset + 2.0f, xOffset + 10 + size + Zamorozka.FONT_MANAGER.arraylist4.getStringWidth(g), yOffset + (size / 6F) + 2.0f + ((font.getHeight() + 2.2f)), 0.5, Colors.getColor(27),
					Colors.getColor(61));
			// RenderingUtils.drawRect(xOffset + 4.0f, yOffset + font.getHeight() + 8.5f,
			// xOffset + size - 4.0f, yOffset + font.getHeight() + 8.8f,
			// Colors.getColor(0));
			Zamorozka.FONT_MANAGER.arraylist4.drawStringWithShadow("" + ChatFormatting.GREEN + "Done: " + ChatFormatting.WHITE + XrauBiposs.done + " / " + ChatFormatting.RED + "All: " + ChatFormatting.WHITE + XrauBiposs.all, xOffset + 25f,
					yOffset + font.getHeight() + 4f, -1);
			// Fonts.comfortaa18.drawStringWithShadow(""+XrauBiposs.done + " / " +
			// XrauBiposs.all, width/2, 1, -1);
			GlStateManager.disableBlend();
		}

		ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
		int counter = 1;
		int yCount = 5;
		int split = -3;
		final List<ItemStack> stuff = new ArrayList<ItemStack>();
		final boolean onwater = mc.player.isEntityAlive() && mc.player.isInsideOfMaterial(Material.WATER);
		EventRenderGui event1 = null;
		EventRenderGui event = event1;
		double posxx = ModuleManager.getModule(KeyStrokes.class).getState() ? 360 : 160;
		if (ModuleManager.getModule(HUD.class).getState() && Zamorozka.settingsManager.getSettingByName("ModelPlayer").getValBoolean()) {
			RenderingUtils.drawEntityOnScreen(35, posxx, 65, mc.player.rotationYaw, mc.player.rotationPitch, mc.player);
		}

		if (ModuleManager.getModule(Logo.class).getState()) {
			String mode2 = Zamorozka.instance.settingsManager.getSettingByName("Array Mode").getValString();
			String mode3 = Zamorozka.instance.settingsManager.getSettingByName("Logo Mode").getValString();
			int color = -1;
			if (mode3.equalsIgnoreCase("ArrayColor")) {
				color = Zamorozka.getClientColor();
			}
			if (mode3.equalsIgnoreCase("OLDColor")) {
				color = 0xFFC24FFB;
			}
			if (mode3.equalsIgnoreCase("FullColor")) {
				color = 0xFFffffff;
			}
			if (mode3.equalsIgnoreCase("OneLetter")) {
				color = TwoColoreffect(new Color(0, 255, 255), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60).getRGB();
			}
			String fps = mc.getDebugFPS() + "FPS";
			int back = (int) Zamorozka.settingsManager.getSettingByName("ArrayBackgroundAplha").getValDouble();
			int back2 = (int) Zamorozka.settingsManager.getSettingByName("ArrayBackgroundBrightness").getValDouble();

			MinecraftFontRenderer mfr = Zamorozka.FONT_MANAGER.QuicksandLight;
			String logo = "Zamorozka";
			if (time1.check(17000)) {
				logo = "Zamorozka";
				time1.reset();
			} else if (time1.check(16000)) {
				logo = "Zamorozka";
			} else if (time1.check(15000)) {
				logo = "Zamorozk_";
			} else if (time1.check(14000)) {
				logo = "Zamoroz_";
			} else if (time1.check(13000)) {
				logo = "Zamoro_";
			} else if (time1.check(12000)) {
				logo = "Zamor_";
			} else if (time1.check(11000)) {
				logo = "Zamo_";
			} else if (time1.check(10000)) {
				logo = "Zam_";
			} else if (time1.check(9000)) {
				logo = "Za_";
			} else if (time1.check(8000)) {
				logo = "Z_";
			} else if (time1.check(7000)) {
				logo = "Za_";
			} else if (time1.check(6000)) {
				logo = "Zam_";
			} else if (time1.check(5000)) {
				logo = "Zamo_";
			} else if (time1.check(4000)) {
				logo = "Zamor_";
			} else if (time1.check(3000)) {
				logo = "Zamoro_";
			} else if (time1.check(2000)) {
				logo = "Zamoroz_";
			} else if (time1.check(1000)) {
				logo = "Zamorozk_";
			} else if (time1.check(0)) {
				logo = "Zamorozka";
			}
			ColoredString logoText = new ColoredString(logo);
			float x1 = .5F;
			float y1 = 1.5F;

			float x2 = x1 * 8 + mfr.getStringWidth(logoText.getString());
			float y2 = y1 * 2 + mfr.getHeight();

			// background RenderUtils2.drawRoundedRect(x1, y1, x2, y2, 0, 0x44000000);
			if (!(mc.currentScreen instanceof ClickGUI)) {
				if (Zamorozka.settingsManager.getSettingByName("LogoBackground").getValBoolean()) {
					RenderUtils2.drawGradientBorderedRect(x1 + 3, y1 + 3, x2 + 3, y2 + 3, Zamorozka.getClientColor(), Zamorozka.getClientColorSecondary(), 0x88000000, 0x66000000);
				}
			}

			logoText.setColor(0, color << 8 >>> 24, color << 16 >>> 24, color << 24 >>> 24);

			// logo
			mfr.drawColoredStringWithShadow(logoText, 5, 5);

		}
		int right = scaledResolution.getScaledWidth();
		counter++;
	}

	public static void renderArrayList(ScaledResolution scaledResolution) {
		double col = -1;
		if (Zamorozka.settingsManager.getSettingByName("ArrayRect").getValBoolean() && ModuleManager.getModule(ArreyList.class).getState()) {
			col = 0;
		} else {
			col = 1;
		}

		double width = scaledResolution.getScaledWidth() + col;
		float[] shadow = new float[] { 0.125F, 0.125F, 0.125F };

		if (ModuleManager.getModule(ArreyList.class).getState()) {
			ArrayList<Module> sortedList = new ArrayList<>(ModuleManager.getModules());
			String mode = Zamorozka.instance.settingsManager.getSettingByName("Array Mode").getValString();
			List<Module> enabledModules = sortedList.stream().filter(Module::getState).sorted(Comparator.comparingDouble(e -> -font.getSortMethod(e.getDisplayName()))).collect(Collectors.toList());
			float yDist = (float) 1;
			int yTotal = 0;
			for (int i = 0; i < enabledModules.size(); i++) {
				yTotal += font.getHeight() + 5;
			}
			for (int i = 0, sortedListSize = enabledModules.size(); i < sortedListSize; i++) {
				Module module = enabledModules.get(i);
				if (module.getDisplayName() != "ClickGUI" && module.getDisplayName() != "SaveModule") {
					Translate translate = module.getTranslate();
					String moduleLabel = module.getDisplayName();
					int listOffset = 10;
					float length = Fonts.elliot18.getStringWidth(moduleLabel);
					float featureX = (float) (width - length - 3.0F);
					boolean enable = module.getState();
					if (enable) {
						translate.interpolate(featureX, yDist, 7);
					} else {
						translate.interpolate((float) (width + 3), yDist, 7);
					}
					double translateX = translate.getX();
					double translateY = translate.getY();
					int color = 0;
					double d = Zamorozka.settingsManager.getSettingByName("CustomOneRed").getValDouble();
					double d1 = Zamorozka.settingsManager.getSettingByName("CustomOneGreen").getValDouble();
					double d2 = Zamorozka.settingsManager.getSettingByName("CustomOneBlue").getValDouble();
					double f = Zamorozka.settingsManager.getSettingByName("CustomTwoRed").getValDouble();
					double f1 = Zamorozka.settingsManager.getSettingByName("CustomTwoGreen").getValDouble();
					double f2 = Zamorozka.settingsManager.getSettingByName("CustomTwoBlue").getValDouble();
					double time = Zamorozka.settingsManager.getSettingByName("CustomColorTime").getValDouble();
					switch (mode.toLowerCase()) {
					case "rainbow":
						color = Colors.rainbow((int) (yDist * 200 * Zamorozka.instance.settingsManager.getSettingByName("Rainbow Spread").getValDouble()),
								(float) Zamorozka.instance.settingsManager.getSettingByName("Rainbow Saturation").getValDouble(), 1.0f);
						break;
					case "greenwhite":
						color = TwoColoreffect(new Color(255, 255, 255), new Color(0, 150, 150), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (yDist * 2.55) / 60).getRGB();
						break;
					case "astolfo":
						color = ColorUtilities.astolfoColors1((int) yDist, yTotal);
						break;
					case "white":
						color = TwoColoreffect(new Color(255, 255, 255), new Color(170, 170, 170), Math.abs(System.currentTimeMillis() / 20L) / 100.0 + 3.0F * (yDist * 2.55) / 60).getRGB();
						break;
					case "pulse":
						color = TwoColoreffect(new Color(0, 255, 255), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (yDist * 2.55) / 60).getRGB();
						break;
					case "custom":
						color = TwoColoreffect(new Color((int) d, (int) d1, (int) d2), new Color((int) f, (int) f1, (int) f2), Math.abs(System.currentTimeMillis() / (long) time) / 100.0 + 3.0F * (yDist * 2.55) / 60).getRGB();
						break;
					case "red-blue":
						color = TwoColoreffect(new Color(255, 25, 25), new Color(0, 150, 255), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60).getRGB();
						break;
					case "grape":
						color = TwoColoreffect(new Color(155, 55, 255), new Color(155, 100, 200), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 3.0F * (2.55) / 60).getRGB();
						break;
					case "none":
						color = -1;
						break;
					case "category":
						color = module.getCategoryColor();
						break;
					}
					int back = (int) Zamorozka.settingsManager.getSettingByName("ArrayBackgroundAplha").getValDouble();
					int back2 = (int) Zamorozka.settingsManager.getSettingByName("ArrayBackgroundBrightness").getValDouble();
					int nextIndex = enabledModules.indexOf(module) + 1;
					if (Zamorozka.settingsManager.getSettingByName("ArrayBackGround").getValBoolean()) {
						RenderingUtils.drawRect(translateX - 2.0D, translateY - 1.0D, width, translateY + listOffset - 1.0D, Colors.getColor(back, back2));
					}
					if (Zamorozka.settingsManager.getSettingByName("ArrayRect").getValBoolean()) {
						RenderingUtils.drawRect(width - 0.5, yDist - 50, width + font.getStringWidth(module.getDisplayName()), translateY + 9.5f, color);
					}
					Module nextModule = null;
					if (enabledModules.size() > nextIndex) {
						nextModule = getNextEnabledModule((ArrayList<Module>) enabledModules, nextIndex);
					}
					if (Zamorozka.settingsManager.getSettingByName("ArrayOutline").getValBoolean()) {
						RenderingUtils.drawRect(translateX - 2.6D, translateY - 1.0D, translateX - 2.0D, translateY + listOffset - 1.0D, color);
						double offsetY = listOffset;
						if (nextModule != null) {
							double dif = (length - Fonts.elliot18.getStringWidth(nextModule.getDisplayName()));
							RenderingUtils.drawRect(translateX - 2.6D, translateY + offsetY - 1.0D, translateX - 2.6D + dif, translateY + offsetY - 0.5D, color);
						} else {
							RenderingUtils.drawRect(translateX - 2.6D, translateY + offsetY - 1.0D, width, translateY + offsetY - 0.6D, color);
						}
					}
					font.drawStringWithShadow(moduleLabel, (float) translateX, (float) translateY, color);
					if (module.getState()) {
						yDist += listOffset;
					}
				}
			}
		}
	}

	private static Module getNextEnabledModule(ArrayList<Module> modules, int startingIndex) {
		for (int i = startingIndex, modulesSize = modules.size(); i < modulesSize; i++) {
			Module module = modules.get(i);
			if (module.getState())
				if (module.getDisplayName() != "ClickGUI" && module.getDisplayName() != "SaveModule") {
					return module;
				}
		}
		return null;
	}

	public static Color TwoColoreffect(Color cl1, Color cl2, double speed) {
		double thing = (speed / 4 % 1);

		float val = MathUtil.clamp(((float) Math.sin((Math.PI * 6) * thing) / 2F + .5F), 0, 1);

		return new Color(MathUtil.lerp(cl1.getRed() / 255F, cl2.getRed() / 255F, val), MathUtil.lerp(cl1.getGreen() / 255F, cl2.getGreen() / 255F, val), MathUtil.lerp(cl1.getBlue() / 255F, cl2.getBlue() / 255F, val));
	}
}