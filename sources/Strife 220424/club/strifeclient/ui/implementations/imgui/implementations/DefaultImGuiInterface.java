package club.strifeclient.ui.implementations.imgui.implementations;

import club.strifeclient.Client;
import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.setting.SerializableEnum;
import club.strifeclient.setting.implementations.BooleanSetting;
import club.strifeclient.setting.implementations.ColorSetting;
import club.strifeclient.setting.implementations.DoubleSetting;
import club.strifeclient.setting.implementations.ModeSetting;
import club.strifeclient.setting.implementations.StringSetting;
import club.strifeclient.ui.implementations.imgui.theme.IImGuiTheme;
import club.strifeclient.ui.implementations.imgui.theme.implementations.DefaultImGuiTheme;
import club.strifeclient.util.math.MathUtil;
import club.strifeclient.util.rendering.ColorUtil;
import imgui.ImGui;
import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;

public class DefaultImGuiInterface implements IImGuiInterface {

	private IImGuiTheme theme;

	public DefaultImGuiInterface() {
		theme = new DefaultImGuiTheme();
	}

	@Override
	public void render() {
		try {
			if (ImGui.begin(Client.NAME)) {
				Arrays.stream(Category.values()).forEach(category -> {
					if (ImGui.beginTabBar("Categories")) {
						if (ImGui.beginTabItem(category.getText())) {
							Client.INSTANCE.getModuleManager().getModules(module -> module.getCategory() == category)
							   .stream()
							   .sorted(Comparator.comparing(Module::getName))
							   .forEach(module -> {
								   if (ImGui.collapsingHeader(module.getName())) {
									   if (ImGui.checkbox("Enabled", module.isEnabled()))
										   module.setEnabled(!module.isEnabled());
									   module.getSettings().forEach(setting -> {
										   if (setting instanceof BooleanSetting) {
											   final BooleanSetting booleanSetting = (BooleanSetting) setting;
											   if (booleanSetting.getDependency().get() && ImGui.checkbox(booleanSetting.getName(), booleanSetting.getImEquivalent()))
												   booleanSetting.setValue(booleanSetting.getImEquivalent().get());
										   } else if (setting instanceof StringSetting) {
											   final StringSetting stringSetting = (StringSetting) setting;
											   if (stringSetting.getDependency().get() && ImGui.inputText(stringSetting.getName(), stringSetting.getImEquivalent()))
												   stringSetting.setValue(stringSetting.getImEquivalent().get());
										   } else if (setting instanceof DoubleSetting) {
											   final DoubleSetting doubleSetting = (DoubleSetting) setting;
											   if (doubleSetting.getDependency().get() && ImGui.sliderFloat(doubleSetting.getName(), doubleSetting.getImEquivalent().getData(), (float) doubleSetting.getMin(), (float) doubleSetting.getMax(), doubleSetting.getIncrement() == 1 ? "%.0f" : "%.2f")) {
												   doubleSetting.setValue(MathUtil.round(doubleSetting.getImEquivalent().get(), 2, doubleSetting.getIncrement()));
												   doubleSetting.getImEquivalent().set(doubleSetting.getFloat());
											   }
										   } else if (setting instanceof ModeSetting) {
											   final ModeSetting<?> modeSetting = (ModeSetting<?>) setting;
											   final String[] values = Arrays.stream(modeSetting.getValues()).map(SerializableEnum::getName).toArray(String[]::new);
											   if (modeSetting.getDependency().get() && ImGui.combo(modeSetting.getName(), modeSetting.getImEquivalent(), values)) {
												   modeSetting.setValue(modeSetting.getImEquivalent().get());
											   }
										   } else if (setting instanceof ColorSetting) {
											   final ColorSetting colorSetting = (ColorSetting) setting;
											   float[] rgb = ColorUtil.toGLColor(colorSetting.getValue());
											   if (colorSetting.getDependency().get() && ImGui.colorEdit4(colorSetting.getName(), rgb)) {
												   colorSetting.setValue(new Color(rgb[0], rgb[1], rgb[2], rgb[3]));
											   }
										   }
									   });
								   }
							   });
							ImGui.endTabItem();
						}
						ImGui.endTabBar();
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ImGui.end();
	}

	@Override
	public IImGuiTheme getTheme() {
		return theme;
	}

	@Override
	public void setTheme(final IImGuiTheme theme) {
		this.theme = theme;
	}
}
