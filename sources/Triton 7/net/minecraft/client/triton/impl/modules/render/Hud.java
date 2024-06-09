package net.minecraft.client.triton.impl.modules.render;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.triton.Triton;
import net.minecraft.client.triton.management.event.EventTarget;
import net.minecraft.client.triton.management.event.events.KeyPressEvent;
import net.minecraft.client.triton.management.event.events.Render2DEvent;
import net.minecraft.client.triton.management.event.events.TickEvent;
import net.minecraft.client.triton.management.module.Module;
import net.minecraft.client.triton.management.module.ModuleManager;
import net.minecraft.client.triton.management.module.Module.Mod;
import net.minecraft.client.triton.management.option.Option.Op;
import net.minecraft.client.triton.ui.ui.TabGui;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderingUtils;

@Mod(enabled = true, shown = false, displayName = "Hud")
public class Hud extends Module {
	@Op(name = "Armor Status")
	private boolean armorStatus;
	@Op(name = "InfoHUD")
	private boolean infoHud;
	@Op(name = "TabGUI")
	private boolean tabGUI;
	@Op(name = "Time")
	private boolean time;
	@Op(name = "Virtue")
	private boolean virtue;
	public static int currentColor;
	private int fadeState;
	private boolean goingUp;

	public Hud() {
		time = true;
		tabGUI = true;
	}

	@EventTarget
	private void onTick(TickEvent event) {
		updateFade();
	}

	@EventTarget
	private void onRender2D(final Render2DEvent event) {
		String extra = (tabGUI && virtue) ? " FPS: §7" + ClientUtils.mc().debugFPS : "";
		ClientUtils.clientFont().drawStringWithShadow(
				Triton.NAME + " b" + (int) Triton.VERSION + " §7" + getTime() + "§f" + extra, 4.0, 3.0,
				Hud.currentColor);
		int y = 2;
		for (final Module mod : ModuleManager.getModulesForRender()) {
			if (mod.drawDisplayName(event.getWidth() - ClientUtils.clientFont().getStringWidth(String.format(
					"%s" + ((mod.getSuffix().length() > 0) ? "§7 %s" : ""), mod.getDisplayName(), mod.getSuffix())) - 2,
					y)) {
				y += 10;
			}
		}
		if (tabGUI) {
			if (virtue) {
				net.minecraft.client.triton.ui.ui.virtue.TabGui.render();
			} else {
				TabGui.render();
			}
		}
		ScaledResolution scaledRes = new ScaledResolution(ClientUtils.mc(), ClientUtils.mc().displayWidth,
				ClientUtils.mc().displayHeight);
		if (infoHud) {
			if (tabGUI) {
				if (!virtue) {
					String fpscount = "FPS: §7" + ClientUtils.mc().debugFPS;
					ClientUtils.clientFont().drawStringWithShadow(fpscount, 3.0, 76.0, Hud.currentColor);
				}
			} else {
				ClientUtils.clientFont().drawStringWithShadow("FPS: §7" + ClientUtils.mc().debugFPS, 3.0, 12.0,
						Hud.currentColor);
			}
			RenderingUtils.drawPotionStatus(scaledRes);
		}
		if (armorStatus) {
			RenderingUtils.drawStuffStatus(scaledRes);
		}
	}

	@EventTarget
	private void onKeypress(KeyPressEvent event) {
		if (!virtue) {
			TabGui.keyPress(event.getKey());
		} else {
			net.minecraft.client.triton.ui.ui.virtue.TabGui.keyPress(event.getKey());
		}
	}

	private int getCategoryColor(final Module module) {
		return this.currentColor;
	}

	private void updateFade() {
		if (this.fadeState >= 20 || this.fadeState <= 0) {
			this.goingUp = !this.goingUp;
		}
		if (this.goingUp) {
			++this.fadeState;
		} else {
			--this.fadeState;
		}
		final double ratio = this.fadeState / 20.0;
		this.currentColor = this.getFadeHex(0xffa6a6a6, 0xfff0f0f0, ratio);
	}

	private int getFadeHex(final int hex1, final int hex2, final double ratio) {
		int r = hex1 >> 16;
		int g = hex1 >> 8 & 0xFF;
		int b = hex1 & 0xFF;
		r += (int) (((hex2 >> 16) - r) * ratio);
		g += (int) (((hex2 >> 8 & 0xFF) - g) * ratio);
		b += (int) (((hex2 & 0xFF) - b) * ratio);
		return r << 16 | g << 8 | b;
	}

	private String getTime() {
		if (time) {
			String time = new SimpleDateFormat("hh:mm a").format(new Date());
			if (time.startsWith("0")) {
				time = time.replaceFirst("0", "");
			}
			return time;
		} else {
			return "";
		}
	}
}
