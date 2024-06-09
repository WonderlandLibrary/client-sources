// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.clickgui.element.elements;

import java.text.DecimalFormat;
import net.andrewsnetwork.icarus.utilities.NahrFont;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.clickgui.element.Element;

public class ElementValueSlider extends Element
{
    protected ConstrainedValue value;
    protected boolean slide;
    protected boolean reset;
    protected final TimeHelper time;
    protected float textWidth;
    protected String name;
    protected int slideX;
    protected int slideX2;
    protected int maxSlide;
    protected int minSlide;
    
    public ElementValueSlider(final ConstrainedValue value) {
        this.reset = true;
        this.time = new TimeHelper();
        Icarus.getFileManager().getFileByName("elementsliderconfiguration").loadFile();
        this.name = value.getName();
        this.value = value;
        this.slide = false;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ConstrainedValue getValue() {
        return this.value;
    }
    
    public int getMaxSlide() {
        return this.maxSlide;
    }
    
    public void setMaxSlide(final int maxSlide) {
        this.maxSlide = maxSlide;
    }
    
    public int getMinSlide() {
        return this.minSlide;
    }
    
    public void setMinSlide(final int minSlide) {
        this.minSlide = minSlide;
    }
    
    public void setValue(final ConstrainedValue value) {
        this.value = value;
    }
    
    public boolean isSliding() {
        return this.slide;
    }
    
    public void setSliding(final boolean slide) {
        this.slide = slide;
    }
    
    public int getSlideX() {
        return this.slideX;
    }
    
    public void setSlideX(final int slideX) {
        this.slideX = slideX;
    }
    
    public void slide(final int mouseX, final int mouseY) {
        if (this.slide) {
            this.slideX = this.slideX2 + mouseX;
            if (this.slideX > this.maxSlide) {
                this.slideX = this.maxSlide;
            }
            if (this.slideX < this.minSlide) {
                this.slideX = this.minSlide;
            }
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.slideX2 = this.slideX - mouseX;
            this.slide = true;
        }
    }
    
    @Override
    public void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            this.slide = false;
            Icarus.getFileManager().getFileByName("elementsliderconfiguration").saveFile();
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        this.slide(mouseX, mouseY);
        if (this.reset) {
            this.time.reset();
            this.reset = false;
        }
        this.handleValue(this.value);
        RenderHelper.drawBorderedCorneredRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + this.getHeight(), 1.0f, Integer.MIN_VALUE, -2145049307);
        if (this.isHovering(mouseX, mouseY)) {
            RenderHelper.drawRect(this.getX(), this.getY() + 4, this.getX() + this.getWidth(), this.getY() + this.getHeight(), 1627389951);
        }
        RenderHelper.drawRect(this.getX(), this.getY() + 4, this.getX() + this.getSlideX(), this.getY() + this.getHeight(), -862535474);
        final String text = String.valueOf(RenderHelper.getPrettyName(this.value.getName().replace(this.value.getModule().getName().toLowerCase(), "").replace("_", ""), " ")) + ": " + this.value.getValue();
        this.textWidth = RenderHelper.getNahrFont().getStringWidth(text);
        RenderHelper.getNahrFont().drawString(text, this.getX() + this.getWidth() / 2 - RenderHelper.getNahrFont().getStringWidth(text) / 2.0f, this.getY() + this.getHeight() / 4 - 2, NahrFont.FontType.SHADOW_THIN, -1, -16777216);
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() + 4 && mouseY <= this.getY() + this.getHeight();
    }
    
    public float getTextWidth() {
        return this.textWidth;
    }
    
    public void setTextWidth(final float textWidth) {
        this.textWidth = textWidth;
    }
    
    public void handleValue(final ConstrainedValue v) {
        try {
            if (v.getValue() instanceof Float && v.getMax() instanceof Float) {
                final double valuePercentage = this.slideX / (Float.valueOf(this.getWidth()) / 100.0f);
                final float max = (float) v.getMax();
                double curValue = max * (valuePercentage / 100.0);
                final DecimalFormat f = new DecimalFormat(".0");
                curValue = Double.parseDouble(f.format(curValue));
                v.setValue((float)(Object)new Double(curValue));
            }
            else if (v.getValue() instanceof Long && v.getMax() instanceof Long) {
                final double valuePercentage = this.slideX / (Float.valueOf(this.getWidth()) / 100.0f);
                final long max2 = (long) v.getMax();
                double curValue2 = max2 * (valuePercentage / 100.0);
                final DecimalFormat f2 = new DecimalFormat(".0");
                curValue2 = Double.parseDouble(f2.format(curValue2));
                v.setValue((long)(Object)new Double(curValue2));
            }
            else if (v.getValue() instanceof Integer && v.getMax() instanceof Integer) {
                final double valuePercentage = this.slideX / (Float.valueOf(this.getWidth()) / 100.0f);
                final int max3 = (int) v.getMax();
                double curValue = max3 * (valuePercentage / 100.0);
                final DecimalFormat f = new DecimalFormat("");
                curValue = Double.parseDouble(f.format(curValue));
                v.setValue((int)(Object)new Double(curValue));
            }
            else if (v.getValue() instanceof Double && v.getMax() instanceof Double) {
                final double valuePercentage = this.slideX / (Float.valueOf(this.getWidth()) / 100.0f);
                final double max4 = (double) v.getMax();
                double curValue2 = max4 * (valuePercentage / 100.0);
                final DecimalFormat f2 = new DecimalFormat(".0");
                curValue2 = Double.parseDouble(f2.format(curValue2));
                v.setValue((double)new Double(curValue2));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
