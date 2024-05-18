// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.theme.themes.one.elements;

import java.text.DecimalFormat;
import org.lwjgl.input.Mouse;
import com.klintos.twelve.utils.NahrFont;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.mod.value.ValueDouble;
import com.klintos.twelve.gui.click.elements.ElementBase;

public class ElementSlider extends ElementBase
{
    private ValueDouble BUTTON_VALUE;
    
    public ElementSlider(final ValueDouble value) {
        super(14);
        this.setValue(value);
    }
    
    @Override
    public void drawElement(final int POSX, final int POSY, final int MOUSEX, final int MOUSEY) {
        this.setPosX(POSX);
        this.setPosY(POSY);
        final boolean isHover = MOUSEX >= this.getPosX() && MOUSEY >= this.getPosY() + 4 && MOUSEX <= this.getPosX() + this.getWidth() / 2 / 2 + 78 && MOUSEY <= this.getPosY() + this.getHeight() + 5;
        final int drag = (int)((this.getValue().getValue() - this.getValue().getMin()) / (this.getValue().getMax() - this.getValue().getMin()) * 104.0);
        GuiUtils.drawFineBorderedRect(this.getPosX(), this.getPosY() + 4, this.getPosX() + drag + ((drag == 104) ? 1 : 0), this.getPosY() + this.getHeight() + 6, -12303292, -44976);
        if (drag != 104) {
            GuiUtils.drawFineBorderedRect(this.getPosX() + drag, this.getPosY() + 4, this.getPosX() + this.getWidth() / 2 / 2 + 79, this.getPosY() + this.getHeight() + 6, -12303292, -13882324);
        }
        GuiUtils.drawFineBorderedRect(this.getPosX(), this.getPosY() + 4, this.getPosX() + this.getWidth() / 2 / 2 + 79, this.getPosY() + this.getHeight() + 6, isHover ? -36752 : -12303292, 0);
        Twelve.getInstance().guiFont.drawCenteredString(String.valueOf(this.getValue().getName()) + ": " + this.getValue().getValue(), this.getPosX() + 52, this.getPosY() + 8, NahrFont.FontType.PLAIN, -1, 0);
        final double value = this.round((MOUSEX - this.getPosX()) / this.getWidth() * this.getValue().getMax(), this.getValue().getRound());
        if (Mouse.isButtonDown(0) && isHover) {
            this.getValue().setValue(value);
        }
        if (this.getValue().getValue() < this.getValue().getMin()) {
            this.getValue().setValue(this.getValue().getMin());
        }
        else if (this.getValue().getValue() > this.getValue().getMax()) {
            this.getValue().setValue(this.getValue().getMax());
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
    
    public ValueDouble getValue() {
        return this.BUTTON_VALUE;
    }
    
    public void setValue(final ValueDouble VALUE) {
        this.BUTTON_VALUE = VALUE;
    }
}
