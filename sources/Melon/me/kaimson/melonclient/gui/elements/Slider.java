package me.kaimson.melonclient.gui.elements;

import me.kaimson.melonclient.gui.*;
import java.util.function.*;
import me.kaimson.melonclient.utils.*;
import me.kaimson.melonclient.*;
import me.kaimson.melonclient.gui.utils.*;
import java.math.*;

public class Slider extends Element
{
    private final float min;
    private final float max;
    private final float step;
    private final String displayString;
    private boolean dragging;
    public float sliderValue;
    private final BiConsumer<Element, Float> consumer;
    
    public Slider(final int x, final int y, final int width, final int height, final float min, final float max, final float step, final float current, final String displayText, final BiConsumer<Element, Float> consumer, final GuiScreen parent) {
        super(x, y, width, height, true, null, parent);
        this.displayString = displayText;
        this.consumer = consumer;
        this.min = min;
        this.max = max;
        this.step = step;
        this.sliderValue = MathUtil.normalizeValue(current, min, max, step);
    }
    
    @Override
    public void renderElement(final float partialTicks) {
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)(this.getY() + 2), (float)(this.getX() + this.width), (float)(this.getY() + this.height + 2), 2.0f, Client.getMainColor(50));
        GLRectUtils.drawRoundedRect((float)this.getX(), (float)(this.getY() + 2), this.getX() + this.sliderValue * (this.width - 8) + 4.0f, (float)(this.getY() + this.height + 2), 2.0f, this.hovered ? Client.getMainColor(150) : Client.getMainColor(100));
        GLRectUtils.drawRoundedRect(this.getX() + this.sliderValue * (this.width - 8), (float)(this.getY() + 2), this.getX() + this.sliderValue * (this.width - 8) + 8.0f, (float)(this.getY() + this.height + 2), 2.2f, Client.getMainColor(255));
        float value = MathUtil.denormalizeValue(this.sliderValue, this.min, this.max, this.step);
        value = this.getRoundedValue(value);
        Client.titleRenderer.drawString(this.displayString, this.getX() - this.width - 5, this.getY() - 1, 16777215);
        Client.titleRenderer.drawString(String.valueOf(value), this.getX() + this.width, this.getY() - 1, 16777215);
    }
    
    @Override
    public void renderBackground(final float partialTicks) {
        super.renderBackground(partialTicks);
    }
    
    @Override
    public boolean mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.enabled && this.hovered) {
            this.sliderValue = (mouseX - (this.getX() + 4)) / (float)(this.width - 8);
            return this.dragging = true;
        }
        return false;
    }
    
    @Override
    public void mouseDragged(final int mouseX, final int mouseY) {
        if (this.dragging) {
            this.sliderValue = (mouseX - (this.getX() + 4)) / (float)(this.width - 8);
            this.sliderValue = MathUtil.normalizeValue(MathUtil.denormalizeValue(this.sliderValue, this.min, this.max, this.step), this.min, this.max, this.step);
            if (this.consumer != null) {
                this.consumer.accept(this, this.sliderValue);
            }
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        this.dragging = false;
        if (this.consumer != null) {
            this.consumer.accept(this, this.sliderValue);
        }
    }
    
    public float getDenormalized() {
        return MathUtil.denormalizeValue(this.sliderValue, this.min, this.max, this.step);
    }
    
    protected float getRoundedValue(final float value) {
        return new BigDecimal(String.valueOf(value)).setScale(2, 4).floatValue();
    }
}
