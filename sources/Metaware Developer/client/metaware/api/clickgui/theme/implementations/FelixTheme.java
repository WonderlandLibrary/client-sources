package client.metaware.api.clickgui.theme.implementations;

import client.metaware.Metaware;
import client.metaware.api.clickgui.component.implementations.BooleanComponent;
import client.metaware.api.clickgui.component.implementations.EnumComponent;
import client.metaware.api.clickgui.component.implementations.SliderComponent;
import client.metaware.api.clickgui.panel.implementations.CategoryPanel;
import client.metaware.api.clickgui.panel.implementations.ModulePanel;
import client.metaware.api.clickgui.theme.Theme;
import client.metaware.api.font.CustomFontRenderer;
import client.metaware.api.module.api.Module;
import client.metaware.impl.utils.render.StringUtils;
import client.metaware.impl.utils.util.other.MathUtils;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class FelixTheme implements Theme {

    private CustomFontRenderer font = Metaware.INSTANCE.getFontManager().currentFont().size(19);

    @Override
    public void drawCategory(CategoryPanel panel, float x, float y, float width, float height) {
        String name = StringUtils.upperSnakeCaseToPascal(panel.category().name());
        Gui.drawRect(x - 1, y - 1, x + width + 1, y + height + 1, 0xff252525);
        font.drawStringWithShadow(name, x + 2, y + panel.height() / 2 - font.getHeight(name) / 2, -1);
    }

    @Override
    public void drawModule(ModulePanel panel, float x, float y, float width, float height) {
        Gui.drawRect(x, y, x + width, y + height, 0xff333333);
        if (panel.module().isToggled()) {
            Gui.drawRect(x, y, x + width, y + height, 0xff5555FF);
        }
        font.drawStringWithShadow(panel.module().getName(), x + 2, y + panel.height() / 2 - font.getHeight(panel.module().getName()) / 2 - 0.5f, -1);
    }

    @Override
    public void drawBindComponent(Module module, float x, float y, float width, float height, boolean focused) {
        String text = "Bind: [" + (focused ? " " : Keyboard.getKeyName(module.getKey())) + "]";
        font.drawStringWithShadow(text, x + 2, y + height / 2 - font.getHeight(text) / 2, -1);
    }

    @Override
    public void drawBooleanComponent(BooleanComponent component, float x, float y, float width, float height, float settingWidth, float settingHeight, int opacity) {
        String label = component.setting().label();
        font.drawStringWithShadow(label, x + 2, y + component.height() / 2 - font.getHeight(label) / 2, -1);
        final float v = y + height / 2 - settingHeight / 2 + settingHeight;
        Gui.drawRect(x + width - settingWidth - 1, y + height / 2 - settingHeight / 2, x + width - 1, v, 0xff1f1f1f);
        if (component.setting().getValue()) {
            Gui.drawRect(x + width - settingWidth - 1.2f, y + height / 2 - settingHeight / 2 + 0.2f, x + width - 1.2f, v - 0.3f, new Color(85, 85, 255, opacity).getRGB());
        }
    }

    @Override
    public void drawEnumComponent(EnumComponent component, float x, float y, float width, float height) {
        String label = component.setting().label();
        font.drawStringWithShadow(label + ": " + component.setting().getValue(), x + 2, y + component.height() / 2 - font.getHeight(label) / 2 - 0.5f, -1);
    }

    @Override
    public void drawSliderComponent(SliderComponent component, float x, float y, float width, float height, float length) {
        Gui.drawRect(x + 2, y + 2, x + length - 2, y + height - 2, new Color(85, 85, 255).getRGB());
        String rep = "";
        switch (component.setting().representation()) {
            case INT:
                rep = "";
            case DOUBLE:
                rep = "";
                break;
            case DISTANCE:
                rep = "m/s";
                break;
            case PERCENTAGE:
                rep = "%";
                break;
            case MILLISECONDS:
                rep = "ms";
                break;
        }
        font.drawStringWithShadow(component.setting().label() + ": " + MathUtils.round(component.setting().getValue(), 2) + rep, x + 2, y + height / 2 - font.getHeight(component.setting().label()) / 2, -1);
    }

}
