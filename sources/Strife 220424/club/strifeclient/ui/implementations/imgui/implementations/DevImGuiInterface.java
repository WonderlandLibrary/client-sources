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
import imgui.extension.texteditor.TextEditor;
import imgui.flag.ImGuiWindowFlags;
import java.awt.Color;
import java.util.Arrays;
import java.util.Comparator;

public class DevImGuiInterface implements IImGuiInterface {

	private IImGuiTheme theme;

	public DevImGuiInterface() {
		theme = new DefaultImGuiTheme();
	}

	private Category currentCategory;
	private TextEditor textEditor;

	@Override
	public void render() {
		if (textEditor == null) {
			textEditor = new TextEditor();
		}
		textEditor.render("David Mag MC");
		try {
			if (ImGui.begin(Client.NAME, ImGuiWindowFlags.NoSavedSettings
				| ImGuiWindowFlags.NoCollapse)) {
				if (ImGui.beginChild(1, ImGui.getContentRegionAvailX() * .25F,
					ImGui.getContentRegionAvailY(), true)) {
					for (Category category : Category.values()) {
						if (ImGui.button(category.getIcon(), ImGui.getContentRegionAvailX(), 48)) {
							currentCategory = category;
						}

					}

				}
				ImGui.endChild();
				ImGui.sameLine();

				if (ImGui.beginChild(2,
					ImGui.getContentRegionAvailX(),
					ImGui.getContentRegionAvailY(),
					true)) {
					Client.INSTANCE.getModuleManager()
						.getModules(module -> module.getCategory() == currentCategory).stream()
						.sorted(Comparator.comparing(Module::getName)).forEach(module -> {
							ImGui.text(module.getName());
							ImGui.spacing();
							if (ImGui.checkbox("Enabled" + "##" + module.getName(),
								module.isEnabled())) {
								module.setEnabled(!module.isEnabled());
							}
							module.getSettings().forEach(setting -> {
								if (setting instanceof BooleanSetting) {
									final BooleanSetting booleanSetting = (BooleanSetting) setting;
									if (booleanSetting.getDependency().get() && ImGui.checkbox(
										booleanSetting.getName() + "##" + module.getName(),
										booleanSetting.getImEquivalent())) {
										booleanSetting.setValue(
											booleanSetting.getImEquivalent().get());
									}
								} else if (setting instanceof StringSetting) {
									final StringSetting stringSetting = (StringSetting) setting;
									if (stringSetting.getDependency().get() && ImGui.inputText(
										stringSetting.getName() + "##" + module.getName(),
										stringSetting.getImEquivalent())) {
										stringSetting.setValue(
											stringSetting.getImEquivalent().get());
									}
								} else if (setting instanceof DoubleSetting) {
									final DoubleSetting doubleSetting = (DoubleSetting) setting;
									if (doubleSetting.getDependency().get() && ImGui.sliderFloat(
										doubleSetting.getName() + "##" + module.getName(),
										doubleSetting.getImEquivalent().getData(),
										(float) doubleSetting.getMin(), (float) doubleSetting.getMax(),
										doubleSetting.getIncrement() == 1 ? "%.0f" : "%.2f")) {
										doubleSetting.setValue(
											MathUtil.round(doubleSetting.getImEquivalent().get(), 2,
												doubleSetting.getIncrement()));
										doubleSetting.getImEquivalent().set(doubleSetting.getFloat());
									}
								} else if (setting instanceof ModeSetting) {
									final ModeSetting<?> modeSetting = (ModeSetting<?>) setting;
									final String[] values = Arrays.stream(modeSetting.getValues())
										.map(SerializableEnum::getName).toArray(String[]::new);
									if (modeSetting.getDependency().get() && ImGui.combo(
										modeSetting.getName() + "##" + module.getName(),
										modeSetting.getImEquivalent(), values)) {
										modeSetting.setValue(modeSetting.getImEquivalent().get());
									}
								} else if (setting instanceof ColorSetting) {
									final ColorSetting colorSetting = (ColorSetting) setting;
									float[] rgb = ColorUtil.toGLColor(colorSetting.getValue());
									try {
										if (colorSetting.getDependency().get() && ImGui.colorEdit3(
											colorSetting.getName() + "##" + module.getName(), rgb)) {
											colorSetting.setValue(
												new Color(rgb[0], rgb[1], rgb[2], rgb[3]));
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});

						});

				}
				ImGui.endChild();
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
