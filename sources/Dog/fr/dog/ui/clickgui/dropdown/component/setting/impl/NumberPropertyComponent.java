package fr.dog.ui.clickgui.dropdown.component.setting.impl;

import fr.dog.property.impl.NumberProperty;
import fr.dog.ui.clickgui.dropdown.component.setting.PropertyComponent;
import fr.dog.util.math.MathsUtil;
import fr.dog.util.render.RenderUtil;
import fr.dog.util.render.font.Fonts;
import fr.dog.util.render.font.TTFFontRenderer;

import java.awt.*;

public class NumberPropertyComponent extends PropertyComponent<NumberProperty> {
    private boolean dragged = false;

    public NumberPropertyComponent(int index, NumberProperty property) {
        super(index, property);

        this.height = 22;
    }

    public void render(int mouseX, int mouseY) {
        RenderUtil.drawRect(x, y, width, height, background);

        String text = property.getLabel() + ": " + property.getValue();

        TTFFontRenderer openSansRegular16 = Fonts.getOpenSansMedium(14);

        openSansRegular16.drawString(text, x + width / 2 - openSansRegular16.getWidth(text) / 2F,
                y + height / 2 - openSansRegular16.getHeight(text) / 2F - 3, Color.WHITE.getRGB());

        final float sliderWidth = this.width - 16;
        final float sliderHeight = 1.5F;

        final float posX = x + 8;
        final float posY = y + height / 2 + 4;

        float min = property.getMinimumValue(), max = property.getMaximumValue();
        float currentValue = property.getValue();

        float multiplier = ((currentValue) - min) / (max - min);

        /*
            Expression doesn't, so I hardcoded the value.
            3 (size) px -> 1.5 (radius) px
         */
        RenderUtil.drawRoundedRect(posX, posY, sliderWidth, sliderHeight, 0.75f, Color.DARK_GRAY);

        { // Drag
            final float dragSize = 3;

            RenderUtil.drawCircle(posX + sliderHeight / 2 + sliderWidth * multiplier - (dragSize + 5) / 2, posY + sliderHeight / 2 - (dragSize + 5) / 2,
                    dragSize + 5, background);
            RenderUtil.drawCircle(posX + sliderHeight / 2 + sliderWidth * multiplier - dragSize / 2, posY + sliderHeight / 2 - dragSize / 2,
                    dragSize, Color.WHITE);
        }

        if (dragged) {
            float percent = Math.min(1, Math.max(0, (mouseX - posX) / sliderWidth));
            float update = MathsUtil.interpolate(min, max, percent);
            property.setValue(update);
        }
    }

    public boolean click(int mouseX, int mouseY, int button) {
        super.click(mouseX, mouseY, button);
        if (hovered && button == 0)
            dragged = true;
        return hovered;
    }

    public void release(int mouseX, int mouseY, int state) {
        dragged = false;
    }

}