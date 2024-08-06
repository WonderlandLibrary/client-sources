package club.strifeclient.ui.implementations.hud.clickgui;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.setting.implementations.*;
import net.minecraft.client.gui.ScaledResolution;

public interface Theme {
    void init();
    void drawCategory(Category category, float x, float y, float width, float height, float origHeight, float partialTicks, ScaledResolution scaledResolution);
    void drawModule(Module module, float x, float y, float width, float height, float origHeight, float partialTicks);
    void drawBooleanSetting(BooleanSetting setting, float x, float y, float width, float height, float origHeight, long start, float partialTicks);
    void drawDoubleSetting(DoubleSetting setting, float x, float y, float width, float height, float origHeight, double progress, float partialTicks);
    void drawModeSetting(ModeSetting<?> setting, String name, float x, float y, float width, float height, float origHeight, float partialTicks);
    void drawBindSetting(Module module, float x, float y, float width, float height, float origHeight, boolean focused, float partialTicks);
    void drawStringSetting(StringSetting setting, float x, float y, float width, float height, float origHeight, boolean focused, float partialTicks);
    void drawColorSetting(ColorSetting setting, float x, float y, float width, float height, float origWidth, float origHeight, float partialTicks);
    void drawMultiSelectSetting(MultiSelectSetting<?> setting, float x, float y, float width, float height, float origWidth, float origHeight, boolean extended, float partialTicks);
}
