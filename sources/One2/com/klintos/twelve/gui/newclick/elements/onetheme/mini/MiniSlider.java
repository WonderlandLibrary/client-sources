// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick.elements.onetheme.mini;

import java.awt.Color;
import java.text.DecimalFormat;
import org.lwjgl.input.Mouse;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.gui.newclick.elements.base.Element;
import com.klintos.twelve.mod.value.ValueDouble;

public class MiniSlider extends MiniElement
{
    private ValueDouble value;
    private boolean dragging;
    private int lastX;
    private int lastY;
    
    public MiniSlider(final int posX, final int posY, final ValueDouble value, final Element parent) {
        super(posX, posY, 92, 14, parent);
        this.value = value;
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        this.updateSlider(mouseX, mouseY);
        final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
        GuiUtils.drawFineBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + this.getWidth() + this.dragX, this.getPosY() + this.getHeight() + this.dragY, isHover ? -36752 : -12303292, -13882324);
        final int drag = (int)(this.value.getValue() / this.value.getMax() * this.getWidth());
        GuiUtils.drawFineBorderedRect(this.getPosX() + this.dragX, this.getPosY() + this.dragY, this.getPosX() + drag + this.dragX, this.getPosY() + this.getHeight() + this.dragY, isHover ? -36752 : -12303292, -44976);
        GuiUtils.drawCentredStringWithShadow(String.valueOf(this.value.getName()) + ": " + ((this.value.getRound() == 0) ? String.valueOf(this.value.getValue()).substring(0, String.valueOf(this.value.getValue()).length() - 2) : this.value.getValue()), this.getPosX() + this.getWidth() / 2 + this.dragX, this.getPosY() + 4 + this.dragY, -1);
    }
    
    private void updateSlider(final int mouseX, final int mouseY) {
        if (!Mouse.isButtonDown(0)) {
            this.setDrag(false);
        } else if (Mouse.isButtonDown(0)) {
        	this.setDrag(true);
        }
        final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
        if (this.isDragging() && isHover) {
            final double v = this.round((double)(mouseX - this.getPosX()) / this.getWidth() * this.value.getMax(), this.value.getRound());
            this.value.setValue(v);
        }
        if (this.value.getValue() < this.value.getMin()) {
            this.value.setValue(this.value.getMin());
        }
        else if (this.value.getValue() > this.value.getMax()) {
            this.value.setValue(this.value.getMax());
        }
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        final boolean isHover = mouseX >= this.getPosX() + this.dragX && mouseY >= this.getPosY() + this.dragY && mouseX <= this.getPosX() + this.getWidth() + this.dragX && mouseY <= this.getPosY() + this.getHeight() + this.dragY;
        if (isHover && button == 0) {
            this.lastX = mouseX;
            this.lastY = mouseY;
            this.setDrag(true);
        }
    }
    
    private double round(final double d, final int r) {
        String round = "#";
        for (int i = 0; i < r; ++i) {
            round = String.valueOf(round) + ".#";
        }
        final DecimalFormat twoDForm = new DecimalFormat(round);
        return Double.valueOf(twoDForm.format(d));
    }
    
    public boolean isDragging() {
        return this.dragging;
    }
    
    public void setDrag(final boolean dragging) {
        this.dragging = dragging;
    }
}
