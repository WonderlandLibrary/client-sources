package client.metaware.api.clickgui.component.implementations;

import client.metaware.api.properties.property.impl.EnumProperty;
import client.metaware.impl.utils.render.RenderUtil;

import java.util.Arrays;

public class EnumComponent extends SettingComponent<EnumProperty> {

    public EnumComponent(EnumProperty setting, float x, float y, float width, float height) {
        super(setting, x, y, width, height);
    }

    public EnumComponent(EnumProperty setting, float x, float y, float width, float height, boolean visible) {
        super(setting, x, y, width, height, visible);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY) {
        if(!visible) return;
        theme.drawEnumComponent(this, x, y, width, height);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if(visible) {
            if(RenderUtil.inBounds(x, y, x + width, y + height, mouseX, mouseY)) {
                int index = Arrays.asList(setting.values()).indexOf(setting.getValue());
                if(mouseButton == 0)
                    if(index + 1 < setting.values().length) {
                        setting.setValue(index++);
                    } else index = 0;
                else
                    if(index - 1 > 0) {
                        setting.setValue(index--);
                    } else index = 0;
                setting.setValue(index);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(char typedChar, int keyCode) {

    }
}
