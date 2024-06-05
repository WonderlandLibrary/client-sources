package zamorozka.modules.WORLD;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import zamorozka.event.EventTarget;
import zamorozka.event.events.EventRender2D;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.Colors;
import zamorozka.ui.EntityUtils1;
import zamorozka.ui.MiscUtils;
import zamorozka.ui.RenderUtils;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.TimerHelper;
import zamorozka.ui.Timerr;

import java.awt.*;
import java.util.ArrayList;

import de.Hero.settings.Setting;

public class Radar extends Module {
	private final Timerr timer = new Timerr();
	private boolean dragging;
	public int scale;
	float hue;

	@Override
	public void setup() {
		ArrayList<String> options = new ArrayList<>();
		options.add("None");
		options.add("RectWhite");
		options.add("RectRainbow");
		Zamorozka.instance.settingsManager.rSetting(new Setting("RadarRect Mode", this, "RectRainbow", options));
		Zamorozka.settingsManager.rSetting(new Setting("RadarPosX", this, 860, 0, 900, true));
		Zamorozka.settingsManager.rSetting(new Setting("RadarPosY", this, 15, 0, 350, true));
		Zamorozka.settingsManager.rSetting(new Setting("RadarSize", this, 100, 30, 300, true));
	}

	public Radar() {
		super("EntityRadar", 0, Category.WORLD);
	}

	@EventTarget
	public void onRender2D(EventRender2D event) {
		Color cl = new Color(-1);
		String mode = Zamorozka.instance.settingsManager.getSettingByName("RadarRect Mode").getValString();
		if (mode.equalsIgnoreCase("RectRainbow")) {
			cl = RenderingUtils.effect(1, 1, 1);
		}
		if (mode.equalsIgnoreCase("RectWhite")) {
			cl = Color.WHITE;
		}
		double psx = Zamorozka.settingsManager.getSettingByName("RadarPosX").getValDouble();
		double psy = Zamorozka.settingsManager.getSettingByName("RadarPosY").getValDouble();
		if (mc.gameSettings.showDebugInfo) {
			psy = 260;
		} else {
			psy = psy;
		}
		final ScaledResolution sr = new ScaledResolution(mc);
		this.scale = 2;
		final int size = (int) Zamorozka.settingsManager.getSettingByName("RadarSize").getValDouble();
		final float xOffset = (float) (sr.getScaledWidth() - size - psx);
		final float yOffset = (float) psy;
		final float playerOffsetX = (float) mc.player.posX;
		final float playerOffsetZ = (float) mc.player.posZ;
		if (!mode.equalsIgnoreCase("None")) {
			RenderingUtils.rectangleBordered(xOffset + 2, yOffset + 0.5, xOffset + size - 2, yOffset + 3, 0.4, Colors.getColor(cl), Colors.getColor(cl));
		}
		// RenderingUtils.rectangleBordered(xOffset + 2.5, yOffset - size - 2.7, xOffset
		// + size - 2.5, yOffset - -2, 0, Colors.getColor(-1), Colors.getColor(-1));
		RenderingUtils.rectangleBordered((double) xOffset + 2.5, (double) yOffset + 2.5, (double) (xOffset + size) - 2.5, (double) (yOffset + size) - 2.5, 0.5, Colors.getColor(11), Colors.getColor(88));
		RenderingUtils.rectangleBordered(xOffset + 3.0f, yOffset + 3.0f, xOffset + size - 3.0f, yOffset + size - 3.0f, 0.2, Colors.getColor(11), Colors.getColor(88));
		RenderingUtils.drawRect((double) xOffset + (size / 2F - 0.5), (double) yOffset + 3.5, (double) xOffset + (size / 2F + 0.2), (double) (yOffset + size) - 3.5, Colors.getColor(255, 100));
		RenderingUtils.drawRect((double) xOffset + 3.5, (double) yOffset + (size / 2F - 0.2), (double) (xOffset + size) - 3.5, (double) yOffset + (size / 2F + 0.5), Colors.getColor(255, 100));
		for (final Object obj : mc.world.getLoadedEntityList()) {
			if (obj instanceof EntityPlayer) {
				final EntityPlayer ent = (EntityPlayer) obj;
				if (ent == mc.player || ent.isInvisible()) {
					continue;
				}
				final float pTicks = this.mc.timer.renderPartialTicks;
				final float posX = (float) ((ent.posX + (ent.posX - ent.lastTickPosX) * pTicks - playerOffsetX) * this.scale);
				final float posZ = (float) ((ent.posZ + (ent.posZ - ent.lastTickPosZ) * pTicks - playerOffsetZ) * this.scale);
				int color;
				if (!MiscUtils.isTeams(ent)) {
					color = new Color(255, 255, 255).getRGB();
				} else {
					color = (mc.player.canEntityBeSeen(ent) ? new Color(255, 0, 0).getRGB() : new Color(255, 255, 0).getRGB());
				}
				final float cos = (float) Math.cos((double) mc.player.rotationYaw * 0.017453292519943295);
				final float sin = (float) Math.sin((double) mc.player.rotationYaw * 0.017453292519943295);
				float rotY = -(posZ * cos - posX * sin);
				float rotX = -(posX * cos + posZ * sin);
				if (rotY > size / 2F - 5) {
					rotY = size / 2F - 5.0f;
				} else if (rotY < -(size / 2F - 5)) {
					rotY = -(size / 2F - 5);
				}
				if (rotX > size / 2F - 5.0f) {
					rotX = size / 2F - 5;
				} else if (rotX < -(size / 2F - 5)) {
					rotX = -(size / 2F - 5.0f);
				}
				RenderingUtils.rectangleBordered((double) (xOffset + size / 2 + rotX) - 1.5, (double) (yOffset + size / 2 + rotY) - 1.5, (double) (xOffset + size / 2 + rotX) + 1.5, (double) (yOffset + size / 2 + rotY) + 1.5, 0.5, color,
						Zamorozka.getClientColor());
			}
		}
	}

	public static Color TwoColoreffect(final Color color, final Color color2, double delay) {
		if (delay > 1.0) {
			final double n2 = delay % 1.0;
			delay = (((int) delay % 2 == 0) ? n2 : (1.0 - n2));
		}
		final double n3 = 1.0 - delay;
		return new Color((int) (color.getRed() * n3 + color2.getRed() * delay), (int) (color.getGreen() * n3 + color2.getGreen() * delay), (int) (color.getBlue() * n3 + color2.getBlue() * delay),
				(int) (color.getAlpha() * n3 + color2.getAlpha() * delay));
	}

}