package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

import java.awt.*;

@ModuleMetaData(name = "Chest Chams", description = "Renders chests through walls", category = ModuleCategoryEnum.RENDER)
public final class ChestChamsModule extends AbstractModule {
    private final BooleanSetting material = new BooleanSetting("Material", true);
    private final BooleanSetting textureVisible = new BooleanSetting("Texture", true);
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);
    private final ColorSetting colorHidden = new ColorSetting("Color Hidden", Color.WHITE);
    private final NumberSetting<Integer> opacity = new NumberSetting<>("Opacity", 255, 1, 255, 1);

    public ChestChamsModule() {
        this.registerSettings(textureVisible, color, colorHidden, material, opacity);
    }

    public boolean isTextureVisible() {
        return textureVisible.getValue();
    }

    public boolean isMaterial() {
        return material.getValue();
    }

    public Color getColor() {
        return color.getValue();
    }

    public Color getColorHidden() {
        return colorHidden.getValue();
    }

    public int opacity() {
        return opacity.getValue();
    }
}