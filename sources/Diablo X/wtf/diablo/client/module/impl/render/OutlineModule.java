package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

import java.awt.*;

@ModuleMetaData(name = "Outline", description = "Draws an outline around entities", category = ModuleCategoryEnum.RENDER)
public final class OutlineModule extends AbstractModule {
    private final NumberSetting<Double> width = new NumberSetting<>("Width", 1.0, 0.5, 5D, 0.5D);
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);

    public OutlineModule() {
        this.registerSettings(width, color);
    }

    public NumberSetting<Double> getWidth() {
        return width;
    }

    public ColorSetting getColor() {
        return color;
    }
}
