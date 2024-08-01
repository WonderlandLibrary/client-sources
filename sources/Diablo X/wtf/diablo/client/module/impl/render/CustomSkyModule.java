package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ColorSetting;

import java.awt.*;

@ModuleMetaData(name = "Custom Sky", description = "Allows you to change the sky color", category = ModuleCategoryEnum.RENDER)
public final class CustomSkyModule extends AbstractModule {
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);

    public CustomSkyModule() {
        this.registerSettings(this.color);
    }

    public ColorSetting getColor() {
        return color;
    }
}
