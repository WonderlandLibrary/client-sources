package zamorozka.modules.VISUALLY;

import java.awt.Color;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.impl.CalendarData;
import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.ibm.icu.util.Calendar;

import de.Hero.clickgui.ClickGUI;
import de.Hero.settings.Setting;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import optifine.CustomColors;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.event.events.EventUpdate;
import zamorozka.event.events.UpdateEvent;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.ZAMOROZKA.YoutuberMode;
import zamorozka.ui.ChatUtils;
import zamorozka.ui.Colors;
import zamorozka.ui.LoginUtils;
import zamorozka.ui.MinecraftFontRenderer;
import zamorozka.ui.MiscUtils;
import zamorozka.ui.RenderUtil;
import zamorozka.ui.RenderUtils2;
import zamorozka.ui.RenderingTools;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.TickRate;
import zamorozka.ui.TimeUtil;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.font.CFontRenderer;
import zamorozka.ui.font.Fonts;

public class HUD extends Module {
	int localMinute = LocalTime.now().getMinute();
	private final CFontRenderer font = Fonts.default18;
	private final CFontRenderer font6 = Fonts.default10;
	private final CFontRenderer font2 = Fonts.default20;

	double lastX = 0;
	double lastZ = 0;

	private final CFontRenderer bigfont = Fonts.default25;
	private final TimeUtil serverTimer = new TimeUtil();
	private boolean isNight = false;
	private boolean military = false;
	private boolean doAmPm = true;
	private double lastPosX = Double.NaN;
	int localHour;
	String amPm = "PM";
	private double lastPosZ = Double.NaN;
	ScaledResolution resolution = new ScaledResolution(mc);
	final double centerX = resolution.getScaledWidth() * 1.11;
	final double centerY = resolution.getScaledHeight_double() * 1.8;
	private ArrayList<Double> distances = new ArrayList<Double>();
	private int curAlpha;

	public static boolean hotbar;

	public HUD() {
		super("HUD", 0, Category.Hud);
	}

	@Override
	public void setup() {
		ArrayList<String> options5 = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("GradientColor Mode", this, "Custom", options5));
		options5.add("Custom");
		options5.add("Client");
		ArrayList<String> options3 = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("Gradient Mode", this, "Top", options3));
		options3.add("Top");
		options3.add("Bottom");
		options3.add("Everywhere");
		ArrayList<String> options4 = new ArrayList<>();
		Zamorozka.settingsManager.rSetting(new Setting("HotbarSlot Mode", this, "Rect", options4));
		options4.add("Rect");
		options4.add("Custom");
		Zamorozka.settingsManager.rSetting(new Setting("GradientBackground", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("GradientRed", this, 0, 0, 255, true));
		Zamorozka.settingsManager.rSetting(new Setting("GradientGreen", this, 150, 0, 255, true));
		Zamorozka.settingsManager.rSetting(new Setting("GradientBlue", this, 255, 0, 255, true));
		Zamorozka.settingsManager.rSetting(new Setting("GradientAlpha", this, 100, 100, 255, false));
		Zamorozka.settingsManager.rSetting(new Setting("SmoothInventoryOpen", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("SmoothInventory Speed", this, 4, 1, 15, false));
		Zamorozka.settingsManager.rSetting(new Setting("Info", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("PotionStatus", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("PotionStatusIcons", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ModelPlayer", this, true));
		// Zamorozka.settingsManager.rSetting(new Setting("InfoBoard", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("BetterHotbar", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("BetterBackground", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("BackgroundRect", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("RenderTNTTag", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("ItemCounter", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("RenderTime", this, true));
		//Zamorozka.settingsManager.rSetting(new Setting("ModuleSound", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("ArmorStatus", this, true));
		Zamorozka.settingsManager.rSetting(new Setting("DrawAltFace", this, false));
		Zamorozka.settingsManager.rSetting(new Setting("CustomHotbarPoints", this, 20, 2, 20, false));
		Zamorozka.settingsManager.rSetting(new Setting("BetterHotbar Speed", this, 15, 1, 70, false));
		Zamorozka.settingsManager.rSetting(new Setting("Zoom Speed", this, 0.03, 0.001, 0.1, false));
	}

	private String getTimeString() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime now = LocalDateTime.now();

		String timeString = dtf.format(now);

		String[] times = timeString.split(":");

		if (Integer.valueOf(times[0]) >= 12 && Integer.valueOf(times[0]) < 24) {
			timeString = timeString.replaceAll("13:", "01:").replaceAll("14:", "02:").replaceAll("15:", "03:").replaceAll("16:", "04:").replaceAll("17:", "05:").replaceAll("18:", "06:").replaceAll("19:", "07:").replaceAll("20:", "08:")
					.replaceAll("21:", "09:").replaceAll("22:", "10:").replaceAll("23:", "11:").replaceAll("24:", "12:");
			timeString += " PM";
		} else if (Integer.valueOf(times[0]) <= 0) {
			timeString = timeString.replaceAll("00:", "12:");
			timeString += " AM";
		} else if (Integer.valueOf(times[0]) <= 12) {
			timeString += " AM";
		}

		return timeString;
	}

	private String getCoordString() {
		String coord = "" + MathHelper.floor(this.mc.player.posX) + " / " + MathHelper.floor(this.mc.player.posY) + " / " + MathHelper.floor(this.mc.player.posZ);

		if (ModuleManager.getModule(YoutuberMode.class).getState() && Zamorozka.settingsManager.getSettingByName("InfoSpoofer").getValBoolean()) {
			String newstr = "";
			char[] rdm = { 'l', 'i', 'j', '\'', ';', ':', '|' };
			for (int k = 0; k < name.length(); k++) {
				char ch = rdm[MiscUtils.randomNumber(rdm.length - 1, 0)];
				newstr = newstr.concat(ch + "");
			}
			coord = newstr;
		}

		return coord;
	}

	public static float globalOffset;

	@EventTarget
	public void on2d(EventRender2D event) {
		float target = (mc.currentScreen instanceof GuiChat) ? 15 : 0;
		float delta = globalOffset - target;

		globalOffset -= delta / Math.max(1, mc.getDebugFPS()) * 10;

		if (!Double.isFinite(globalOffset)) {
			globalOffset = 0;
		}

		if (globalOffset > 15) {
			globalOffset = 15;
		}
		if (globalOffset < 0) {
			globalOffset = 0;
		}

		MinecraftFontRenderer quickSand = Zamorozka.FONT_MANAGER.Quicksand;
		int quickSandHeight = quickSand.getHeight();

		MinecraftFontRenderer quickSandLight = Zamorozka.FONT_MANAGER.QuicksandLight;
		int quickSandLightHeight = quickSandLight.getHeight();

		String timeString = getTimeString();
		String coord = getCoordString();
		double prevX = mc.player.posX - mc.player.prevPosX;
		double prevZ = mc.player.posZ - mc.player.prevPosZ;
		double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
		double motionX = mc.player.moveStrafing * (lastDist * 200);
		double motionZ = mc.player.moveForward * (lastDist * 200);
		lastX += (motionX - lastX) / 4;
		lastZ += (motionZ - lastZ) / 4;
		double spd2 = lastDist * 15.3571428571;
		String speed = String.format("%.2f", spd2);
		String blockps = "Blocks Per/Sec: " + speed;
		String fps = "FPS: " + mc.getDebugFPS();
		// String ping = "PING: " + ((mc.isSingleplayer() ? 0 :
		// mc.getCurrentServerData().pingToServer));

		float height = 10 + globalOffset;
		if (Zamorozka.settingsManager.getSettingByName("RenderTime").getValBoolean()) {
			Fonts.elliot37.drawStringWithShadow(timeString, event.getResolution().getScaledWidth() - 3 - Fonts.elliot37.getStringWidth(timeString), event.getResolution().getScaledHeight() - Fonts.elliot37.getHeight() - height + 9.5f, 0xFFFFFFFF);

		}

		if (Zamorozka.settingsManager.getSettingByName("Info").getValBoolean()) {
			if (Zamorozka.settingsManager.getSettingByName("Info").getValBoolean()) {
				double checkX = Zamorozka.settingsManager.getSettingByName("DrawAltFace").getValBoolean() ? 25 : 4;
				/*
				 * Fonts.elliot20.drawStringWithShadow(blockps, font.getStringWidth(fps) - 15,
				 * event.getResolution().getScaledHeight() - height - font.getHeight() - 1.5,
				 * -1);
				 */

				// p4olkin eblan pomenjaesh shrift zarezu
				String fof = "" + mc.getDebugFPS();
				Fonts.elliot20.drawStringWithShadow("FPS: ", checkX - 2, event.getResolution().getScaledHeight() - height - font.getHeight() - 1.5, Zamorozka.getClientColor());
				Fonts.elliot20.drawStringWithShadow("" + fof, checkX + 24, event.getResolution().getScaledHeight() - height - font.getHeight() - 1.5, -1);
				Fonts.elliot20.drawStringWithShadow("PING: ", checkX + Fonts.elliot18.getStringWidth(fof) + 30, event.getResolution().getScaledHeight() - height - font.getHeight() - 1.5, Zamorozka.getClientColor());
				String uid = "UID: \2477#" + "User";
				String build = "Build: \2477" + "Latest 0.5.3";
				if (!(Zamorozka.settingsManager.getSettingByName("ItemCounter").getValBoolean() || Zamorozka.settingsManager.getSettingByName("RenderTime").getValBoolean() || Zamorozka.settingsManager.getSettingByName("BetterBackground").getValBoolean())) {
					font.drawStringWithShadow(uid, event.getResolution().getScaledWidth() - font.getStringWidth(uid) - 2, event.getResolution().getScaledHeight() - height, -1);
					font.drawStringWithShadow(build, event.getResolution().getScaledWidth() - font.getStringWidth(uid) - font.getStringWidth(build) - 6, event.getResolution().getScaledHeight() - height, -1);
				} else {
					int tt = Zamorozka.settingsManager.getSettingByName("BackgroundRect").getValBoolean() ? 24 : 22;
					font.drawStringWithShadow(uid, event.getResolution().getScaledWidth() - font.getStringWidth(uid) - 2, event.getResolution().getScaledHeight() - height - tt, -1);
					font.drawStringWithShadow(build, event.getResolution().getScaledWidth() - font.getStringWidth(uid) - font.getStringWidth(build) - 6, event.getResolution().getScaledHeight() - height - tt, -1);
				}
				if (!mc.isSingleplayer()) {
					if (mc.getCurrentServerData() != null) {
						Fonts.elliot20.drawStringWithShadow("" + (mc.getCurrentServerData().pingToServer), checkX + Fonts.elliot18.getStringWidth(fof) + 63, event.getResolution().getScaledHeight() - height - font.getHeight() - 1.5, -1);
					}
				}
				Fonts.elliot20.drawStringWithShadow("XYZ: ", checkX - 2, event.getResolution().getScaledHeight() - height, Zamorozka.getClientColor());
				Fonts.elliot20.drawStringWithShadow(coord, checkX + 24, event.getResolution().getScaledHeight() - height, -1);
				// Zamorozka.FONT_MANAGER.arraylist5.drawStringWithShadow(ping,
				// event.getResolution().getScaledWidth() - 120,
				// event.getResolution().getScaledHeight() - height - font.getHeight() - 2,
				// -1s);
				List tabplayer = GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable) mc.player.connection.getPlayerInfoMap());
				for (Object tabs : tabplayer) {
					NetworkPlayerInfo tab = (NetworkPlayerInfo) tabs;
					if (mc.world.getPlayerEntityByUUID(tab.getGameProfile().getId()) == mc.player) {
						mc.getTextureManager().bindTexture(tab.getLocationSkin());
						if (Zamorozka.settingsManager.getSettingByName("DrawAltFace").getValBoolean()) {
							Gui.drawScaledCustomSizeModalRect(0, (float) (event.getResolution().getScaledHeight() + -height - 13), 8.0f, 8.0f, 8, 8, 22, 22, 64.0f, 64.0f);
						}
						GlStateManager.bindTexture(0);
					}
				}
			}
		}

		if (Zamorozka.settingsManager.getSettingByName("PotionStatus").getValBoolean()) {
			drawPotionStatus(event.getResolution());
		}
		if (Zamorozka.settingsManager.getSettingByName("ArmorStatus").getValBoolean()) {
			ScaledResolution sr = new ScaledResolution(mc);
			if (mc.currentScreen instanceof ClickGUI)
				return;
			renderStuffStatus3(sr);
		}
		if (Zamorozka.settingsManager.getSettingByName("ItemCounter").getValBoolean() && (!(mc.currentScreen instanceof GuiChat))) {
			String totems = "Totems: " + totemCount();
			String gapple = "Gapples: " + gappleCount();
			String pearls = "EnderPearls: " + pearlCount();

			quickSand.drawStringWithShadow(totems, event.getResolution().getScaledWidth() - quickSand.getStringWidth(totems) - 3, event.getResolution().getScaledHeight() - 60, 0xFFFFFFFF);
			quickSand.drawStringWithShadow(gapple, event.getResolution().getScaledWidth() - quickSand.getStringWidth(gapple) - 3.5, event.getResolution().getScaledHeight() - 48, 0xFFFFFFFF);
			quickSand.drawStringWithShadow(pearls, event.getResolution().getScaledWidth() - quickSand.getStringWidth(pearls) - 3, event.getResolution().getScaledHeight() - 36, 0xFFFFFFFF);

		}
	}

	@EventTarget
	public void onUpdate(EventUpdate event) {
		if (!Double.isNaN(lastPosX) && !Double.isNaN(lastPosZ)) {
			double differenceX = Math.abs(lastPosX - mc.player.posX);
			double differenceZ = Math.abs(lastPosZ - mc.player.posZ);
			double distance = Math.sqrt(differenceX * differenceX + differenceZ * differenceZ);

			distances.add(distance);
			if (distances.size() > 20)
				distances.remove(0);
		}

		lastPosX = mc.player.posX;
		lastPosZ = mc.player.posZ;
	}

	public static int rainbow2(final int delay) {
		double rainbowState = Math.ceil((double) ((System.currentTimeMillis() + delay) / 4L));
		rainbowState %= 360.0;
		return Color.getHSBColor((float) (rainbowState / 360.0), 0.2f, 2.0f).getRGB();
	}

	private void drawPotionStatus(ScaledResolution sr) {
		float offset = globalOffset;
		float pY = -2;
		float HUY = -2;
		List<PotionEffect> potions = new ArrayList<>();

		for (Object o : mc.player.getActivePotionEffects())
			potions.add((PotionEffect) o);
		potions.sort(Comparator.comparingDouble(effect -> -mc.fontRendererObj.getStringWidth(I18n.format(Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName())).getName()))));

		for (PotionEffect effect : potions) {
			Potion potion = Potion.getPotionById(CustomColors.getPotionId(effect.getEffectName()));
			String name = I18n.format(potion.getName());
			String PType = "";
			if (effect.getAmplifier() == 1) {
				name = name + " II";
			} else if (effect.getAmplifier() == 2) {
				name = name + " III";
			} else if (effect.getAmplifier() == 3) {
				name = name + " IV";
			}
			if ((effect.getDuration() < 600) && (effect.getDuration() > 300)) {
				PType = PType + ": " + Potion.getDurationString(effect);
			} else if (effect.getDuration() < 300) {
				PType = PType + ": " + Potion.getDurationString(effect);
			} else if (effect.getDuration() > 600) {
				PType = PType + ": " + Potion.getDurationString(effect);
			}
			int l1 = Zamorozka.settingsManager.getSettingByName("PotionStatusIcons").getValBoolean() ? 18 : 3;
			int l2 = Zamorozka.settingsManager.getSettingByName("PotionStatusIcons").getValBoolean() ? 17 : 2;
			int ll = Zamorozka.settingsManager.getSettingByName("BackgroundRect").getValBoolean() ? 43 : 41;
			int d = Zamorozka.settingsManager.getSettingByName("Info").getValBoolean() ? 20 : 9;
			if (!(Zamorozka.settingsManager.getSettingByName("ItemCounter").getValBoolean() || Zamorozka.settingsManager.getSettingByName("RenderTime").getValBoolean() || Zamorozka.settingsManager.getSettingByName("BetterBackground").getValBoolean() || Zamorozka.settingsManager.getSettingByName("BackgroundRect").getValBoolean())) {
				Fonts.elliot20.drawStringWithShadow(name, sr.getScaledWidth() - Fonts.elliot20.getStringWidth(name + PType) - l1, sr.getScaledHeight() - d + pY - offset, potion.getLiquidColor());
				Fonts.elliot20.drawStringWithShadow(PType, sr.getScaledWidth() - Fonts.elliot20.getStringWidth(PType) - l2, sr.getScaledHeight() - d + pY - offset, -1);
				pY -= 11;
			} else if (Zamorozka.settingsManager.getSettingByName("ItemCounter").getValBoolean() || Zamorozka.settingsManager.getSettingByName("RenderTime").getValBoolean() || Zamorozka.settingsManager.getSettingByName("BetterBackground").getValBoolean()) {
				Fonts.elliot20.drawStringWithShadow(name, sr.getScaledWidth() - Fonts.elliot20.getStringWidth(PType + name) - l1, sr.getScaledHeight() - ll + pY - offset, potion.getLiquidColor());
				Fonts.elliot20.drawStringWithShadow(PType, sr.getScaledWidth() - Fonts.elliot20.getStringWidth(PType) - l2, sr.getScaledHeight() - ll + pY - offset, -1);
				pY -= 11;
			}
			if (Zamorozka.settingsManager.getSettingByName("PotionStatusIcons").getValBoolean() && getState() && !nullCheck()) {
				mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
				if (potion.hasStatusIcon()) {
					int icon = potion.getStatusIconIndex();
					int i = 0;
					GL11.glScalef((float) 0.8f, (float) 0.8f, (float) 0.8f);
					Gui.drawTexturedModalRect(sr.getScaledWidth() - Fonts.elliot20.getStringWidth(PType) + 247, sr.getScaledHeight() - Fonts.elliot20.getStringHeight(name + PType) + 115 + HUY - offset, icon % 8 * 18, 198 + icon / 8 * 18, 18, 18);
					HUY -= Fonts.elliot20.getStringHeight(name + PType) + 7;
					GL11.glScalef((float) 1.25f, (float) 1.25f, (float) 1.25f);
					GlStateManager.disableBlend();
				}
			}
		}
	}

	public double getDistTraveled() {
		double total = 0;
		for (double d : distances) {
			total += d;
		}
		return total;
	}

	public static double HALF_PI = Math.PI / 2;

	private double getX(double rad) {
		return Math.sin(rad) * (30);
	}

	private double getY(double rad) {
		final double epicPitch = MathHelper.clamp(getRenderEntity().rotationPitch + 30f, -90f, 90f);
		final double pitchRadians = Math.toRadians(epicPitch);
		return Math.cos(rad) * Math.sin(pitchRadians) * (30);
	}

	private void renderStuffStatus3(ScaledResolution scaledRes) {
		float offset = (mc.player.isCreative() ? -15 : 0) + globalOffset;

		for (int slot = 3, xOffset = 0; slot >= 0; slot--) {
			ItemStack stack = mc.player.inventory.armorItemInSlot(slot);
			double st = mc.player.isInsideOfMaterial(Material.WATER) ? 85 : 74;
			double st1 = mc.player.isInsideOfMaterial(Material.WATER) ? 132 : 112;
			GuiIngame gi = new GuiIngame(mc);
			if (stack != null && !stack.func_190926_b()) {
				RenderingTools.renderItem(stack, scaledRes.getScaledWidth() / 2 + 10 - xOffset, (int) (scaledRes.getScaledHeight() - st + 17 - offset));
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				double gg = Zamorozka.settingsManager.getSettingByName("BackgroundRect").getValBoolean() ? 16 : 18;
				/*
				 * bigfont.drawStringWithShadow(stack.getMaxDamage() - stack.getItemDamage() +
				 * "", scaledRes.getScaledWidth() + 22 - xOffset * 2 + (stack.getMaxDamage() -
				 * stack.getItemDamage() >= 100 ? 4 : (stack.getMaxDamage() -
				 * stack.getItemDamage() <= 100 && stack.getMaxDamage() - stack.getItemDamage()
				 * >= 10 ? 7 : 11)), scaledRes.getScaledHeight() * 2 - st1 + gg - offset * 2,
				 * 0xFFFFFF);
				 */
				GL11.glScalef(2F, 2F, 2F);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				xOffset -= 20;
			}
		}
	}

	private int totemCount() {
		int count = 0;
		for (int i = 0; i < 45; ++i) {
			if (!mc.player.inventory.getStackInSlot(i).func_190926_b() && mc.player.inventory.getStackInSlot(i).getItem() == Items.field_190929_cY) {
				count++;
			}
		}
		return count;
	}

	private int gappleCount() {
		int count = 0;
		for (int i = 0; i < 45; ++i) {
			if (!mc.player.inventory.getStackInSlot(i).func_190926_b() && mc.player.inventory.getStackInSlot(i).getItem() == Items.GOLDEN_APPLE) {
				count++;
			}
		}
		return count;
	}

	private int pearlCount() {
		int count = 0;
		for (int i = 0; i < 45; ++i) {
			if (!mc.player.inventory.getStackInSlot(i).func_190926_b() && mc.player.inventory.getStackInSlot(i).getItem() == Items.ENDER_PEARL) {
				count++;
			}
		}
		return count;
	}

	private static double getPosOnCompass(Direction dir) {
		double yaw = Math.toRadians(MathHelper.wrapDegrees(getRenderEntity().rotationYaw));
		int index = dir.ordinal();
		return yaw + (index * HALF_PI);
	}

	private enum Direction {
		N, W, S, E
	}

	public static Entity getRenderEntity() {
		return mc.getRenderViewEntity();
	}
}
