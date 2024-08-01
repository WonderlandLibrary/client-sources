package wtf.diablo.client.module.impl.render;

import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ColorSetting;

import java.awt.*;

@ModuleMetaData(
        name = "Glint",
        description = "Changes the glint of the sword",
        category = ModuleCategoryEnum.RENDER
)
public final class GlintModule extends AbstractModule {
    private final ColorSetting color = new ColorSetting("Color", new Color(255, 255, 255));

    public GlintModule()
    {
        this.registerSettings(color);
    }

    public int getColor(){
        return this.isEnabled() ? color.getValue().getRGB() : -8372020;
    }


}
