package wtf.diablo.client.module.impl.render;

import net.minecraft.entity.player.EntityPlayer;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.BooleanSetting;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

import java.awt.*;
import java.util.ArrayList;

@ModuleMetaData(name = "Chams", description = "Renders players through walls", category = ModuleCategoryEnum.RENDER)
public final class ChamsModule extends AbstractModule {
    private final BooleanSetting material = new BooleanSetting("Material", true);
    private final BooleanSetting textureVisible = new BooleanSetting("Texture", true);
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);
    private final ColorSetting colorHidden = new ColorSetting("Color Hidden", Color.WHITE);
    private final NumberSetting<Integer> opacity = new NumberSetting("Opacity", 255, 1, 255, 1);
    private final BooleanSetting disableHitColor = new BooleanSetting("Disable Hit Color", false);


    private final ArrayList<EntityPlayer> players = new ArrayList<>();

    public ChamsModule() {
        this.registerSettings(textureVisible, color, colorHidden, material, opacity, disableHitColor);
    }

    @Override
    public void onDisable() {
        players.clear();
        super.onDisable();
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

    public boolean isHitColorDisabled() {
        return disableHitColor.getValue();
    }
}
