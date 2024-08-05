package fr.dog.ui.clickgui.dropdown.component.setting.impl;

import fr.dog.Dog;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.theme.Theme;
import fr.dog.ui.clickgui.dropdown.component.setting.PropertyComponent;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;

import java.awt.*;

public class BooleanPropertyComponent extends PropertyComponent<BooleanProperty> {
    public BooleanPropertyComponent(int index, BooleanProperty property) {
        super(index, property);

        this.height = 12;
    }

    public void render(int mouseX, int mouseY) {
        RenderUtil.drawRect(x, y, width, height, background);

        String text = property.getLabel();

        TTFFontRenderer openSansRegular16 = Fonts.getOpenSansMedium(14);

        Theme theme = Dog.getInstance().getThemeManager().getCurrentTheme();

        openSansRegular16.drawString(text,
                x + height / 2 - openSansRegular16.getHeight(text) / 2F,
                y + height / 2 - openSansRegular16.getHeight(text) / 2F, Color.WHITE.getRGB());

        final float circleSize = 5F;
        RenderUtil.drawCircle(x + width - circleSize * 1.5F - height / 2 + mc.fontRendererObj.FONT_HEIGHT / 2F,
                y + height / 2 - circleSize / 2, circleSize, property.getValue() ? theme.getColor(3, 0) : background.darker());
    }

    public boolean click(int mouseX, int mouseY, int button) {
        super.click(mouseX, mouseY, button);
        if (hovered && button == 0)
            property.setValue(!property.getValue());
        return hovered;
    }
}
