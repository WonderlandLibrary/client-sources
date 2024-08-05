package fr.dog.ui.clickgui.dropdown.component.setting.impl;

import fr.dog.property.impl.ModeProperty;
import fr.dog.ui.clickgui.dropdown.component.setting.PropertyComponent;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;

import java.awt.*;

public class ModePropertyComponent extends PropertyComponent<ModeProperty> {
    public ModePropertyComponent(int index, ModeProperty property) {
        super(index, property);

        this.height = 12;
    }

    public void render(int mouseX, int mouseY) {
        RenderUtil.drawRect(x, y, width, height, background);

        String text = property.getLabel() + ": §7" + property.getValue();

        TTFFontRenderer openSansRegular16 = Fonts.getOpenSansMedium(14);

        openSansRegular16.drawString(text,
                x + height / 2 - openSansRegular16.getHeight(text) / 2F,
                y + height / 2 - openSansRegular16.getHeight(text) / 2F, Color.WHITE.getRGB());
    }

    public boolean click(int mouseX, int mouseY, int button) {
        super.click(mouseX, mouseY, button);
        if (hovered) {
            switch (button) {
                case 0 -> {
                    int newIndex = property.getIndex() + 1;
                    if (newIndex >= property.getValues().length) {
                        newIndex = 0;
                    }
                    property.setIndexValue(newIndex);
                }
                case 1 -> {
                    int newIndex = property.getIndex() - 1;
                    if (newIndex < 0) {
                        newIndex = property.getValues().length - 1;
                    }
                    property.setIndexValue(newIndex);
                }
            }
        }
        return hovered;
    }
}