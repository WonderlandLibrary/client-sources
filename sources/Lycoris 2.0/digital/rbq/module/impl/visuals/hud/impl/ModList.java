/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 */
package digital.rbq.module.impl.visuals.hud.impl;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;
import digital.rbq.core.Autumn;
import digital.rbq.module.Module;
import digital.rbq.module.impl.visuals.hud.Component;
import digital.rbq.module.impl.visuals.hud.HUDMod;
import digital.rbq.utils.render.Palette;
import digital.rbq.utils.render.Translate;

public final class ModList extends Component {
	private float hue = 1.0f;

	public ModList(HUDMod parent) {
		super(parent);
	}

	@Override
	public void draw(ScaledResolution sr) {
		HUDMod hud = this.getParent();
		int width = sr.getScaledWidth();
		int height = sr.getScaledHeight();
		FontRenderer fr = hud.defaultFont.getValue() != false ? ModList.mc.fontRendererObj : ModList.mc.fontRenderer;
		boolean bottom = hud.arrayListPosition.getValue() == HUDMod.ArrayListPosition.BOTTOM;
		List<Module> sortedList = this.getSortedModules(fr);
		Color modListColor = (Color) hud.color.getValue();
		float translationFactor = 14.4f / (float) Minecraft.getDebugFPS();
		int listOffset = 10;
		int y = bottom ? height - listOffset : 0;
		this.hue += translationFactor / 100.0f;
		if (this.hue > 1.0f) {
			this.hue = 0.0f;
		}
		float h = this.hue;
		GL11.glEnable((int) 3042);
		int sortedListSize = sortedList.size();
		for (int i = 0; i < sortedListSize; ++i) {
			int color;
			Module module = sortedList.get(i);
			Translate translate = module.getTranslate();
			String moduleLabel = module.getDisplayLabel();
			float length = fr.getStringWidth(moduleLabel);
			float featureX = (float) width - length - 2.0f;
			boolean enable = module.isEnabled();
			if (bottom) {
				if (enable) {
					translate.interpolate(featureX, y + 1, translationFactor);
				} else {
					translate.interpolate(width, height + 1, translationFactor);
				}
			} else if (enable) {
				translate.interpolate(featureX, y + 1, translationFactor);
			} else {
				translate.interpolate(width, -listOffset - 1, translationFactor);
			}
			double translateX = translate.getX();
			double translateY = translate.getY();
			boolean visible = bottom ? translateY < (double) height : translateY > (double) (-listOffset);
			boolean bl = bottom ? translateY < (double) height : (visible = translateY > (double) (-listOffset));
			if (!visible)
				continue;
			switch ((HUDMod.ArrayListColor) ((Object) hud.modListColorMode.getValue())) {
			case PULSING: {
				color = Palette.fade(modListColor, 100, sortedList.indexOf(module) * 2 + 10).getRGB();
				break;
			}
			case RAINBOW: {
				color = Color.HSBtoRGB(h, 0.7f, 1.0f);
				break;
			}
			default: {
				color = modListColor.getRGB();
			}
			}
			int nextIndex = sortedList.indexOf(module) + 1;
			Module nextModule = null;
			if (sortedList.size() > nextIndex) {
				nextModule = this.getNextEnabledModule(sortedList, nextIndex);
			}
			if (hud.modListBackground.getValue().booleanValue()) {
				Gui.drawRect(translateX - 2.0, translateY - 1.0, width, translateY + (double) listOffset - 1.0,
						new Color(13, 13, 13,
								(int) (255.0f * ((Double) this.parent.modListBackgroundAlpha.getValue()).floatValue()))
										.getRGB());
			}
			if (hud.modListSideBar.getValue().booleanValue()) {
				Gui.drawRect(translateX + (double) length + 1.0, translateY - 1.0, width,
						translateY + (double) listOffset - 1.0, color);
			}
			if (hud.modListOutline.getValue().booleanValue()) {
				double offsetY;
				Gui.drawRect(translateX - 2.5, translateY - 1.0, translateX - 2.0,
						translateY + (double) listOffset - 1.0, color);
				double d = offsetY = bottom ? -0.5 : (double) listOffset;
				if (nextModule != null) {
					double dif = length - (float) fr.getStringWidth(nextModule.getDisplayLabel());
					Gui.drawRect(translateX - 2.5, translateY + offsetY - 1.0, translateX - 2.5 + dif,
							translateY + offsetY - 0.5, color);
				} else {
					Gui.drawRect(translateX - 2.5, translateY + offsetY - 1.0, width, translateY + offsetY - 0.5,
							color);
				}
			}
			fr.drawStringWithShadow(moduleLabel, (float) translateX, (float) translateY, color);
			if (module.isEnabled()) {
				y += bottom ? -listOffset : listOffset;
			}
			h += translationFactor / 6.0f;
		}
	}

	private Module getNextEnabledModule(List<Module> modules, int startingIndex) {
		int modulesSize = modules.size();
		for (int i = startingIndex; i < modulesSize; ++i) {
			Module module = modules.get(i);
			if (!module.isEnabled())
				continue;
			return module;
		}
		return null;
	}

	private List<Module> getSortedModules(FontRenderer fr) {
		ArrayList<Module> sortedList = new ArrayList<Module>(
				(Collection<Module>) Autumn.MANAGER_REGISTRY.moduleManager.getModules());
		sortedList.removeIf(Module::isHidden);
		sortedList.sort(Comparator.comparingDouble(e -> -fr.getStringWidth(e.getDisplayLabel())));
		return sortedList;
	}
}
