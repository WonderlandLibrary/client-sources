// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.theme.themes.one.gui;

import java.util.Iterator;
import com.klintos.twelve.gui.click.theme.themes.one.elements.ElementRadar;
import com.klintos.twelve.gui.click.theme.themes.one.elements.ElementSlider;
import com.klintos.twelve.mod.value.ValueDouble;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.gui.click.elements.ElementBase;
import com.klintos.twelve.gui.click.theme.themes.one.elements.ElementButton;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.gui.click.GuiClick;
import com.klintos.twelve.gui.click.theme.themes.one.panel.PanelBaseClient;

public class GuiClickClient extends PanelBaseClient
{
    private GuiClick GUI_SCREEN;
    private String GUI_TYPE;
    
    public GuiClickClient(final GuiClick SCREEN, final String NAME, final int POSX, final int POSY, final String TYPE) {
        super(NAME, POSX, POSY);
        this.GUI_SCREEN = SCREEN;
        this.GUI_TYPE = TYPE;
    }
    
    @Override
    public void addElements() {
        this.loadElements();
    }
    
    public void loadElements() {
        this.getElements().clear();
        for (final Mod MOD : Twelve.getInstance().getModHandler().getMods()) {
            if (MOD.getModCategory().toString() == this.getType()) {
                this.addElement(new ElementButton(MOD));
            }
        }
        for (final Value VALUE : Twelve.getInstance().getModHandler().getValues()) {
            if (this.getType() == "Values" && VALUE instanceof ValueDouble) {
                final ValueDouble v = (ValueDouble)VALUE;
                this.addElement(new ElementSlider(v));
            }
        }
        if (this.getType() == "Radar") {
            this.addElement(new ElementRadar());
        }
    }
    
    public GuiClick getScreen() {
        return this.GUI_SCREEN;
    }
    
    public void setScreen(final GuiClick SCREEN) {
        this.GUI_SCREEN = SCREEN;
    }
    
    public String getType() {
        return this.GUI_TYPE;
    }
    
    public void setType(final String TYPE) {
        this.GUI_TYPE = TYPE;
    }
    
    @Override
    public void drawElements(final int POSX, final int POSY) {
        int currentPosition = 0;
        for (final ElementBase i$2 : this.getElements()) {
            if (i$2 instanceof ElementButton) {
                if (!this.getOpen()) {
                    continue;
                }
                i$2.drawElement(this.getPosX() + 3, this.getPosY() + currentPosition + 15, POSX, POSY);
                currentPosition += i$2.getHeight() + 3;
                this.setOpenHeight(currentPosition + 3);
            }
            else if (i$2 instanceof ElementSlider) {
                if (!this.getOpen()) {
                    continue;
                }
                i$2.drawElement(this.getPosX() + 3, this.getPosY() + currentPosition + 15, POSX, POSY);
                currentPosition += i$2.getHeight() + 3;
                this.setOpenHeight(currentPosition + 3);
            }
            else {
                if (!(i$2 instanceof ElementRadar) || !this.getOpen()) {
                    continue;
                }
                i$2.drawElement(this.getPosX() + 3, this.getPosY() + currentPosition + 9, POSX, POSY);
                this.setOpenHeight(i$2.getHeight() + 6);
            }
        }
    }
}
