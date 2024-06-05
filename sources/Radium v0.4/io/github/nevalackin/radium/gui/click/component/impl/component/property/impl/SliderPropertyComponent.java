package io.github.nevalackin.radium.gui.click.component.impl.component.property.impl;

import io.github.nevalackin.radium.gui.click.component.Component;
import io.github.nevalackin.radium.gui.click.component.impl.component.property.PropertyComponent;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.property.impl.Representation;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.OGLUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.math.BigDecimal;

public final class SliderPropertyComponent extends Component implements PropertyComponent {

    private static final int SLIDER_COLOR = new Color(151, 151, 151, 255).getRGB();

    private final DoubleProperty doubleProperty;

    private boolean sliding;

    public SliderPropertyComponent(Component parent,
                                   DoubleProperty property,
                                   int x,
                                   int y,
                                   int width,
                                   int height) {
        super(parent, property.getLabel(), x, y, width, height);

        this.doubleProperty = property;
    }

    @Override
    public void drawComponent(ScaledResolution scaledResolution, int mouseX, int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);

        int x = getX();
        int y = getY();
        int width = getWidth();
        int height = getHeight();
        double min = doubleProperty.getMin();
        double max = doubleProperty.getMax();
        Double dValue = doubleProperty.getValue();
        Representation representation = doubleProperty.getRepresentation();
        boolean isInt = representation == Representation.INT || representation == Representation.MILLISECONDS;
        double value;
        if (isInt)
            value = dValue.intValue();
        else
            value = dValue;
        double sliderPercentage = ((value - min) / (doubleProperty.getMax() - min));
        boolean hovered = isHovered(mouseX, mouseY);

        if (sliding) {
            if (hovered)
                doubleProperty.setValue(
                    MathHelper.clamp_double(
                            roundToFirstDecimalPlace((mouseX - x) * (max - min) / width + min),
                            min,
                            max));
            else
                sliding = false;
        }

        String name = getName();
        int middleHeight = getHeight() / 2;
        String valueString;

        if (isInt)
            valueString = Integer.toString((int) value);
        else
            valueString = Double.toString(value);

        switch (representation) {
            case PERCENTAGE:
                valueString += '%';
                break;
            case MILLISECONDS:
                valueString += "ms";
                break;
            case DISTANCE:
                valueString += 'm';
        }
        float valueWidth = Wrapper.getFontRenderer().getWidth(valueString) + 2;
        float overflowWidth = (Wrapper.getFontRenderer().getWidth(name) + 3) - (width - valueWidth);
        boolean needOverflowBox = overflowWidth > 0;
        boolean showOverflowBox = hovered && needOverflowBox;
        boolean needScissorBox = needOverflowBox && !hovered;

        Gui.drawRect(x - (showOverflowBox ? overflowWidth : 0), y,
                x + width,
                y + height,
                getSecondaryBackgroundColor(hovered));
        Gui.drawRect(x, y, x + width * sliderPercentage, y + height, SLIDER_COLOR);
        if (needScissorBox)
            OGLUtils.startScissorBox(scaledResolution, x, y, (int) (width - valueWidth - 4), height);
        Wrapper.getFontRenderer().drawStringWithShadow(name,
                x + 2 - (showOverflowBox ? overflowWidth : 0),
                y + middleHeight - 3,
                0xFFFFFF);
        if (needScissorBox)
            OGLUtils.endScissorBox();
        Wrapper.getFontRenderer().drawStringWithShadow(valueString, x + width - valueWidth, y + middleHeight - 3, -1);
    }

    private double roundToFirstDecimalPlace(double value) {
        double inc = doubleProperty.getIncrement();
        double halfOfInc = inc / 2.0D;
        double floored = Math.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) return new BigDecimal(Math.ceil(value / inc) * inc).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        else return new BigDecimal(floored).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public void onMouseClick(int mouseX, int mouseY, int button) {
        if (!sliding && button == 0 && isHovered(mouseX, mouseY))
            sliding = true;
    }

    @Override
    public void onMouseRelease(int button) {
        this.sliding = false;
    }

    @Override
    public Property<?> getProperty() {
        return doubleProperty;
    }
}
