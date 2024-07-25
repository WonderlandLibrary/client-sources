package club.bluezenith.module.modules.render;

import club.bluezenith.BlueZenith;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.KeyPressEvent;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.Value;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ColorValue;
import club.bluezenith.module.value.types.ListValue;
import club.bluezenith.util.font.FontUtil;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TabGUI extends Module {
	private final List<ModuleCategory> categories = Arrays.asList(ModuleCategory.values());
	private List<Module> currentCategoryModuleList;
	private List<Value<?>> currentModuleValueList;

	private int categoryIndex = 0;
	private ModuleCategory selectedCategory = ModuleCategory.COMBAT;

	private int moduleIndex = 0;
	private Module selectedModule;

	private int valueIndex = 0;
	private Value<?> selectedValue;
	private Value<?> actuallySelectedValue;

	private boolean categoryExpanded;
	private boolean moduleExpanded;
	private boolean valueSelected;

	private float categoryOffset;
	private float categoryOffset1;

	public TabGUI() {
		super("TabGUI", ModuleCategory.RENDER);
	}

	@Override
	public void onEnable() {
		selectedCategory = ModuleCategory.values()[0];
	}

	@SuppressWarnings("unused")
	@Listener
	public void onRender2D(Render2DEvent event) {
		if(mc.currentScreen == null)
		Keyboard.enableRepeatEvents(true);

		int y = 20;
		float moduleY = 20;
		RenderUtil.rect(5, y, 75, y + 85, ColorUtil.get(0, 0, 0, 150));

		y += 1.2;
		for (final ModuleCategory c : ModuleCategory.values()) {
			FontUtil.mulishMedium35.drawString(c.displayName, 7, y, -1);
			if (selectedCategory == c) {
				categoryOffset = RenderUtil.animate(y, categoryOffset, 0.15f);
				RenderUtil.rect(70.8f, categoryOffset + 0.5f, 72.5f, categoryOffset + 9.5f, -1);
				if (categoryExpanded) {
					currentCategoryModuleList = BlueZenith.getBlueZenith().getModuleManager().getModulesByCategory(selectedCategory);
					for (final Module mod : currentCategoryModuleList) {
						RenderUtil.rect(76, moduleY, 156, moduleY + 12.0f, ColorUtil.get(0, 0, 0, 150));
						if (selectedModule == null) selectedModule = currentCategoryModuleList.get(0);

						FontUtil.mulishMedium35.drawString(mod.getName(), 78, moduleY, mod.getState() ? -1 : new Color(160, 160, 160, 255).getRGB());
						if (selectedModule == mod) {
							float valueY = 20;
							categoryOffset1 = RenderUtil.animate(moduleY, categoryOffset1, 0.15f);
							RenderUtil.rect(151.8F, categoryOffset1 + 1.5f, 153.5f, categoryOffset1 + FontUtil.mulishMedium35.FONT_HEIGHT, -1); //moduleY + 1f, 74, moduleY + 10.3f
							if (moduleExpanded) {
								float x = 280;
								currentModuleValueList = mod.getValues();
								for (final Value<?> value : currentModuleValueList) {
									if(value instanceof ColorValue || value instanceof ListValue) continue;
									if (selectedValue == null) selectedValue = currentModuleValueList.get(0);
									RenderUtil.rect(157, valueY, 291, valueY + 12.2, new Color(1, 1, 1, 150));
									if (selectedValue == value) {
										RenderUtil.rect(287.5F, valueY + 1.5f, 289, valueY + FontUtil.mulishMedium35.FONT_HEIGHT, -1);
									}
									final String str = value instanceof BooleanValue ? ((BooleanValue) value).get() ? "On" : "Off"
											: value.get() instanceof Number ? String.valueOf(MathUtil.round(((Number) value.get()).floatValue(), 1)) : String.valueOf(value.get());
									final float nameWidth = FontUtil.mulishMedium35.getStringWidth(str);
									FontUtil.mulishMedium35.drawString(value.name, 159, valueY, actuallySelectedValue == value ? new Color(110, 110, 110, 255).getRGB() : -1);
									FontUtil.mulishMedium35.drawString(str, x - nameWidth + (selectedValue == value ? 3 : 7), valueY, new Color(150, 150, 150, 255).getRGB());
									valueY += 12;
								}
							}
						}
						moduleY += 12;
					}
				}
			}
			y += 12;
		}
		if(mc.currentScreen == null)
		Keyboard.enableRepeatEvents(false);
	}

	@SuppressWarnings("unused")
	@Listener
	public void onKeyPress(KeyPressEvent event) {
		switch (event.keyCode) {
			case 200: //200 = key up
				if (!categoryExpanded) {
					if (categoryIndex > 0) categoryIndex--;
					else categoryIndex = categories.size() - 1;

					selectedCategory = categories.get(categoryIndex);
				} else if (!moduleExpanded) {
					if (moduleIndex > 0) moduleIndex--;
					else moduleIndex = currentCategoryModuleList.size() - 1;
					selectedModule = currentCategoryModuleList.get(moduleIndex);
				} else if (!valueSelected) {
					if (valueIndex - 1 < 0) valueIndex = currentModuleValueList.size() - 1;
					else valueIndex--;
					selectedValue = currentModuleValueList.get(valueIndex);
				} else if (actuallySelectedValue != null) actuallySelectedValue.next();
				break;

			case 208: //208 = key down
				if (!categoryExpanded) {
					if (categoryIndex >= categories.size() - 1)
						categoryIndex = 0;
					else categoryIndex++;

					selectedCategory = categories.get(categoryIndex);
				} else if (!moduleExpanded) {
					if (moduleIndex >= currentCategoryModuleList.size() - 1) {
						moduleIndex = 0;
					} else moduleIndex++;
					selectedModule = currentCategoryModuleList.get(moduleIndex);
				} else if (!valueSelected) {
					if (valueIndex >= currentModuleValueList.size() - 1)
						valueIndex = 0;
					else valueIndex++;
					selectedValue = currentModuleValueList.get(valueIndex);
				} else if (actuallySelectedValue != null) actuallySelectedValue.previous();
				break;

			case 203:  //203 = key left
				if (categoryExpanded) {
					if (!moduleExpanded) {
						categoryExpanded = false;
						selectedModule = null;
						moduleIndex = 0;
					} else if (!valueSelected) {
						moduleExpanded = false;
						valueIndex = 0;
					}
				}
				break;

			case 205: //205 = key right
				if (selectedModule == null) {
					categoryExpanded = true;
				} else {
					if (!moduleExpanded && !selectedModule.getValues().isEmpty()) {
						moduleExpanded = true;
					}
				}
				break;

			case 28: //28 = key enter
				if (moduleExpanded) {
					if (!valueSelected) {
						actuallySelectedValue = selectedValue;
						valueSelected = true;
					} else {
						valueSelected = false;
						actuallySelectedValue = null;
					}
				} else if(selectedModule != null) selectedModule.toggle();
				break;
		}
	}
}
