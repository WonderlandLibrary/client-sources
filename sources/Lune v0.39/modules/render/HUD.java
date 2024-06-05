package me.superskidder.lune.modules.render;

import java.awt.Color;
import java.util.ArrayList;

import me.superskidder.lune.Lune;
import me.superskidder.lune.customgui.CustomGuiManager;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.render.Hudmode.*;
import me.superskidder.lune.modules.render.Hudmode.Old;
import me.superskidder.lune.events.EventRender2D;
import me.superskidder.lune.font.CFontRenderer;
import me.superskidder.lune.font.FontLoaders;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.timer.TimerUtil;
import me.superskidder.lune.values.type.Bool;
import me.superskidder.lune.values.type.Mode;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;

public class HUD extends Mod {

	public static Bool BackGround = new Bool("BackGround", true);
	public static Bool<Boolean> font = new Bool<>("Font", true);
	public static Bool<Boolean> ChatFont = new Bool<>("ChatFont", false);

	public static Bool Rainbow = new Bool("Rainbow", false);
	public static Bool TabUi = new Bool("TabUI", true);
	public static Bool noRender = new Bool("noRender", true);
	public static Bool alpharainbow = new Bool("AlphaRainbow", true);
	public static Mode mod = new Mode("Mode", HUDMode.values(), HUDMode.New);
    public static int themeColor;
    public TimerUtil timer = new TimerUtil();
	public ScaledResolution sr;

	public HUD() {
		super("HUD", ModCategory.Render,"Display some information");
		this.addValues(BackGround, font,ChatFont, Rainbow, alpharainbow, mod,TabUi,noRender);
		this.setStage(true);
	}

	@Override
	public void onEnabled() {
		if (mc.theWorld == null)
			return;


		// 排序&初始化
		CFontRenderer font1;
		font1 = FontLoaders.C18;
		ArrayList<Mod> sorted = new ArrayList<Mod>();
		for (Mod m : ModuleManager.modList) {
			sorted.add(m);
		}
		sorted.sort((o1, o2) -> font1
				.getStringWidth(o2.getName() + (o2.getDisplayName() == null ? "" : " " + o2.getDisplayName()))
				- font1.getStringWidth(o1.getName() + (o1.getDisplayName() == null ? "" : " " + o1.getDisplayName())));
		int y = 2;

		for (Mod m : sorted) {

			if (m.getState()) {
				m.setYAnim(y);

				y += 12;
			}
		}
	}

	@EventTarget
	private void renderHud(EventRender2D event) {
		if(Lune.tabUI == null){
			Lune.tabUI = new TabUI();
			Lune.tabUI.init();
		}
		sr = new ScaledResolution(mc);

		CustomGuiManager.drawGuiPre();

		FontLoaders.F16.drawString(GuiMainMenu.NOTICE,
				(float) (sr.getScaledWidth_double() / 2 - FontLoaders.F16.getStringWidth(GuiMainMenu.NOTICE) / 2), 10,
				new Color(255, 255, 255, 100).getRGB());
		if (mod.getValue() == HUDMode.Old) {
			Old.renderHud();
		} else if (mod.getValue() == HUDMode.New) {
			New.renderHud();
		} else if (mod.getValue() == HUDMode.Flux) {
			FluxHUD.renderHud();
		} else if (mod.getValue() == HUDMode.Sigma) {
			Sigma.renderHud();
		} else if (mod.getValue() == HUDMode.Lune) {
			LuneHUD.renderHud();
		}

		CustomGuiManager.drawGuiPost();

	}

	public static enum HUDMode {
		Old, New, Flux, Sigma, Lune
	}
}
