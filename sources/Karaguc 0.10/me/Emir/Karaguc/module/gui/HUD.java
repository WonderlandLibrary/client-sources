package me.Emir.Karaguc.module.gui;

import de.Hero.settings.Setting;
import me.Emir.Karaguc.Karaguc;
import me.Emir.Karaguc.event.EventTarget;
import me.Emir.Karaguc.event.events.Event2D;
import me.Emir.Karaguc.module.Category;
import me.Emir.Karaguc.module.Module;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class HUD extends Module {
	private FontRenderer fr = mc.fontRendererObj;

	public HUD() {
		super("HUD", 0, Category.GUI);
	}

	@Override
	public void setup() {

		ArrayList<String> modes = new ArrayList<String>();
		modes.add("Arraylist1");
		modes.add("Arraylist2");
		modes.add("Arraylist3");
		modes.add("Karaguc1");
		modes.add("Default");
		Karaguc.instance.settingsManager.rSetting(new Setting("HUD Mode", this, "Default", modes));

		Karaguc.instance.settingsManager.rSetting(new Setting("Rainbow", this, true));
		Karaguc.instance.settingsManager.rSetting(new Setting("Logo", this, true));
		Karaguc.instance.settingsManager.rSetting(new Setting("ArrayList", this, true));
		Karaguc.instance.settingsManager.rSetting(new Setting("Keystrokes", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("ArmorHud", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("Hotbar", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("Chat", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("RainScreen", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("SnowScreen", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("Font", this, false));
		Karaguc.instance.settingsManager.rSetting(new Setting("ClickGui Scale", this, 5, 0.2, 10, false));

	}

	@EventTarget
	public void onRender(Event2D event) {
		ScaledResolution sr = new ScaledResolution(mc);

		GL11.glPushMatrix();

		GL11.glScalef(1.5F, 1.5F, 0.5F);

		if (Karaguc.instance.settingsManager.getSettingByName("Logo").getValBoolean())
			fr.drawStringWithShadow(Karaguc.instance.name, 5, 5,
					(Karaguc.instance.settingsManager.getSettingByName("Rainbow").getValBoolean())
							? getRainbow(6000, -15) : new Color(0, 255, 232).getRGB());

		GL11.glPopMatrix();

		if (Karaguc.instance.settingsManager.getSettingByName("ArrayList").getValBoolean())
			renderArrayList(sr);
	}

	private void renderArrayList(ScaledResolution sr) {
		GlStateManager.pushMatrix();
		int yStart = 1;
		ArrayList<Module> mods = new ArrayList();
		for (Module m : Karaguc.instance.moduleManager.getModules())
			if (m.isToggled())
				mods.add(m);
		mods.sort((o1, o2) -> {
			return fr.getStringWidth(o2.getDisplayName()) - fr.getStringWidth(o1.getDisplayName());
		});

		for (Iterator var4 = mods.iterator(); var4.hasNext(); yStart += fr.FONT_HEIGHT + 1) {
			Module module = (Module) var4.next();
			int startX = sr.getScaledWidth() - fr.getStringWidth(module.getDisplayName()) - 7;
			Gui.drawRect((double) startX, (double) (yStart - 2), (double) sr.getScaledWidth(),
					(double) (yStart + fr.FONT_HEIGHT), new Color(23, 23, 23).getRGB());
			Gui.drawRect((double) (sr.getScaledWidth() - 2), (double) (yStart - 1), (double) sr.getScaledWidth(),
					(double) (yStart + fr.FONT_HEIGHT),
					(Karaguc.instance.settingsManager.getSettingByName("Rainbow").getValBoolean())
							? getRainbow(6000, -15 * yStart) : new Color(0, 255, 232).getRGB());
			Gui.drawVerticalLine(startX - 1, yStart - 2, yStart + fr.FONT_HEIGHT,
					(Karaguc.instance.settingsManager.getSettingByName("Rainbow").getValBoolean())
							? getRainbow(6000, -15 * yStart) : new Color(0, 255, 232).getRGB());
			Gui.drawHorizontalLine(startX - 1, sr.getScaledWidth(), yStart + fr.FONT_HEIGHT,
					(Karaguc.instance.settingsManager.getSettingByName("Rainbow").getValBoolean())
							? getRainbow(6000, -15 * yStart) : new Color(0, 255, 232).getRGB());
			fr.drawStringWithShadow(module.getDisplayName(), startX + 3, yStart,
					(Karaguc.instance.settingsManager.getSettingByName("Rainbow").getValBoolean())
							? getRainbow(6000, -15 * yStart) : new Color(0, 255, 232).getRGB());
		}

		GlStateManager.popMatrix();
	}

	private int getRainbow(int speed, int offset) {
		float hue = (System.currentTimeMillis() + offset) % speed;
		hue /= speed;
		return Color.getHSBColor(hue, 1F, 1F).getRGB();
	}
}
