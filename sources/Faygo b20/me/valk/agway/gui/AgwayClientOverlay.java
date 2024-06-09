package me.valk.agway.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.valk.Vital;
import me.valk.agway.AgwayClient;
import me.valk.event.EventListener;
import me.valk.event.events.screen.EventRenderScreen;
import me.valk.module.Module;
import me.valk.overlay.VitalOverlay;
import me.valk.utils.Wrapper;
import me.valk.utils.render.PrizonRenderUtils;
import me.valk.utils.render.RenderUtil;
import me.valk.utils.render.ScreenUtil;
import me.valk.utils.render.VitalFontRenderer;
import me.valk.utils.render.VitalFontRenderer.FontObjectType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AgwayClientOverlay extends VitalOverlay {

	public float hue = 0.0f;
	public static VitalFontRenderer font = new VitalFontRenderer().createFontRenderer(FontObjectType.CFONT,
			new Font("Shine Personal Use", Font.PLAIN, 26));
	public static VitalFontRenderer arial = new VitalFontRenderer().createFontRenderer(FontObjectType.CFONT,
			new Font("Arial", Font.PLAIN, 18));

	public AgwayClientOverlay() {
		super("AgwayClientOverlay");
	}

	public void renderWaterMark() {
		String theme = AgwayClient.tabTheme.getValue();
		if (theme.equalsIgnoreCase("Faygo")) {

			GlStateManager.pushMatrix();
			double scale = 1.0;
			GlStateManager.scale(scale, scale, scale);
			this.font.drawStringWithShadow("Faygo", 3, 4, new Color(255, 155, 0).getRGB());
			GlStateManager.popMatrix();

		}
	}

	@Override
	public void render() {
		if (AgwayClient.hide)
			return;

		int y = 2;

		renderWaterMark();

		RenderUtil.setColor(Color.WHITE);

		List<Module> mods = new ArrayList<Module>();

		for (Module module : Vital.getManagers().getModuleManager().getContents())
			mods.add(module);
		float h = this.hue;

		Collections.sort(mods, new ModuleComparator());

		for (Module module : mods) {
			if (module.getState() && module.getData().isVisible()) {

				this.hue += 0.04;

				if (this.hue > 255.0f) {
					this.hue = 0.0f;
				}

				final Color color = Color.getHSBColor(h / 255.0f, 1.0f, 1.0f);
		
				int width = this.arial.getStringWidth(module.getDisplayName() + " ");
		
				this.arial.drawStringWithShadow(module.getDisplayName(), ScreenUtil.getWidth() - width,
						y, PrizonRenderUtils.ColorUtils.transparency(y, 1.0));
				y += Wrapper.getFontRenderer().getHeight() + 1;
			}
			h += 2.5f;
		}
	}

	public static class ModuleComparator implements Comparator<Module> {

		@Override
		public int compare(Module o1, Module o2) {
			if (arial.getStringWidth(o1.getDisplayName()) < arial
					.getStringWidth(o2.getDisplayName())) {
				return 1;
			} else if (arial.getStringWidth(o1.getDisplayName()) > arial
					.getStringWidth(o2.getDisplayName())) {
				return -1;
			} else {
				return 0;
			}
		}

	}

	@EventListener
	public void onRender(EventRenderScreen event) {
		
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);

		int y = sr.getScaledHeight() - (Minecraft.getMinecraft().fontRendererObj.getHeight() + 2);

		for (Object object : Wrapper.getPlayer().getActivePotionEffects()) {
			PotionEffect effect = (PotionEffect) object;
			Potion potion = Potion.potionTypes[effect.getPotionID()];

			String add = "";

			switch (effect.getAmplifier()) {
			case 1: {
				add = " II";
				break;
			}
			case 2: {
				add = " III";
				break;
			}
			case 3: {
				add = " IV";
				break;
			}
			}

			String text = I18n.format(potion.getName()) + "" + add + "§7 " + Potion.getDurationString(effect);
			float x = sr.getScaledWidth() - (Wrapper.getFontRenderer().getStringWidth(text) + 1);
			Wrapper.getFontRenderer().drawStringWithShadow(text, x, y + 1, potion.getLiquidColor());
			y -= Wrapper.getFontRenderer().getHeight() + 2;
		}

	}
}
