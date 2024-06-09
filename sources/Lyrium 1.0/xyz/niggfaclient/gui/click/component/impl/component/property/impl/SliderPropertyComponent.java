// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.gui.click.component.impl.component.property.impl;

import xyz.niggfaclient.property.Property;
import java.math.BigDecimal;
import net.minecraft.client.gui.Gui;
import xyz.niggfaclient.module.impl.render.HUD;
import xyz.niggfaclient.utils.render.RenderUtils;
import java.awt.Color;
import xyz.niggfaclient.font.Fonts;
import net.minecraft.util.MathHelper;
import xyz.niggfaclient.property.impl.Representation;
import net.minecraft.client.gui.ScaledResolution;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.gui.click.component.impl.component.property.PropertyComponent;
import xyz.niggfaclient.gui.click.component.Component;

public final class SliderPropertyComponent extends Component implements PropertyComponent
{
    private final DoubleProperty doubleProperty;
    private boolean sliding;
    
    public SliderPropertyComponent(final Component parent, final DoubleProperty property, final int x, final int y, final int width, final int height) {
        super(parent, property.getName(), x, y, width, height);
        this.doubleProperty = property;
    }
    
    @Override
    public void drawComponent(final ScaledResolution scaledResolution, final int mouseX, final int mouseY) {
        super.drawComponent(scaledResolution, mouseX, mouseY);
        final int x = (int)(this.getX() - 0.7);
        final int y = this.getY();
        final int width = this.getWidth() + 2;
        final int height = this.getHeight() + 3;
        final double min = this.doubleProperty.getMin();
        final double max = this.doubleProperty.getMax();
        final Double dValue = this.doubleProperty.getValue();
        final Representation representation = this.doubleProperty.getRepresentation();
        final boolean isInt = representation == Representation.INT || representation == Representation.MILLISECONDS;
        final double value = isInt ? dValue.intValue() : ((double)dValue);
        final double sliderPercentage = (value - min) / (max - min);
        final boolean hovered = this.isHovered(mouseX, mouseY);
        if (this.sliding) {
            if (mouseX >= x - 0.5f && mouseY >= y - 0.5f && mouseX <= x + width + 0.5f && mouseY <= y + height + 0.5f) {
                this.doubleProperty.setValue(MathHelper.clamp_double(this.roundToIncrement((mouseX - x) * (max - min) / (width - 1) + min), min, max));
            }
            else {
                this.sliding = false;
            }
        }
        final String name = this.getName();
        final int middleHeight = this.getHeight() / 2;
        String valueString = isInt ? Integer.toString((int)value) : Double.toString(value);
        switch (representation) {
            case PERCENTAGE: {
                valueString += '%';
                break;
            }
            case MILLISECONDS: {
                valueString += "ms";
                break;
            }
            case DISTANCE: {
                valueString += 'm';
                break;
            }
        }
        final float valueWidth = (float)(Fonts.sf19.getStringWidth(valueString) + 2);
        final float overflowWidth = Fonts.sf19.getStringWidth(name) + 3 - (width - valueWidth);
        final boolean needOverflowBox = overflowWidth > 0.0f;
        final boolean showOverflowBox = hovered && needOverflowBox;
        final boolean needScissorBox = needOverflowBox && !hovered;
        RenderUtils.drawCustomRounded(x - (showOverflowBox ? overflowWidth : 0.0f), (float)y, (float)(x + width), (float)(y + height), 0.0f, 0.0f, 4.0f, 4.0f, this.isHovered(mouseX, mouseY) ? new Color(85, 85, 85).getRGB() : new Color(45, 45, 45).getRGB());
        Gui.drawRect(x, y, x + width * sliderPercentage, y + height, HUD.hudColor.getValue());
        if (needScissorBox) {
            RenderUtils.startScissorBox(x, y, (int)(width - valueWidth - 4.0f), height);
        }
        Fonts.sf19.drawStringWithShadow(name, x + 1 - (showOverflowBox ? overflowWidth : 0.0f), (float)(y + middleHeight - 3), -1);
        if (needScissorBox) {
            RenderUtils.endScissorBox();
        }
        Fonts.sf19.drawStringWithShadow(valueString, x + width - valueWidth, (float)(y + middleHeight - 3), -1);
    }
    
    private double roundToIncrement(final double value) {
        final double inc = this.doubleProperty.getIncrement();
        final double halfOfInc = inc / 2.0;
        final double floored = StrictMath.floor(value / inc) * inc;
        if (value >= floored + halfOfInc) {
            return new BigDecimal(StrictMath.ceil(value / inc) * inc).setScale(2, 4).doubleValue();
        }
        return new BigDecimal(floored).setScale(2, 4).doubleValue();
    }
    
    @Override
    public void onMouseClick(final int mouseX, final int mouseY, final int button) {
        if (!this.sliding && button == 0 && this.isHovered(mouseX, mouseY)) {
            this.sliding = true;
        }
    }
    
    @Override
    public void onMouseRelease(final int button) {
        this.sliding = false;
    }
    
    @Override
    public Property<?> getProperty() {
        return this.doubleProperty;
    }
}
