package client.metaware.api.clickgui.theme;


import client.metaware.api.clickgui.component.implementations.BooleanComponent;
import client.metaware.api.clickgui.component.implementations.EnumComponent;
import client.metaware.api.clickgui.component.implementations.SliderComponent;
import client.metaware.api.clickgui.panel.implementations.CategoryPanel;
import client.metaware.api.clickgui.panel.implementations.ModulePanel;
import client.metaware.api.module.api.Module;

public interface Theme {
    void drawCategory(CategoryPanel panel, float x, float y, float width, float height);
    void drawModule(ModulePanel panel, float x, float y, float width, float height);
    void drawBindComponent(Module module, float x, float y, float width, float height, boolean focused);
    void drawBooleanComponent(BooleanComponent component, float x, float y, float width, float height, float settingWidth, float settingHeight, int opacity);
    void drawEnumComponent(EnumComponent component, float x, float y, float width, float height);
    void drawSliderComponent(SliderComponent component, float x, float y, float width, float height, float length);
}
