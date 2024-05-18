// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.theme.themes.one.panel;

import com.klintos.twelve.utils.NahrFont;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.utils.GuiUtils;
import com.klintos.twelve.gui.click.panel.PanelBase;

public abstract class PanelBaseClient extends PanelBase
{
    protected PanelBaseClient(final String NAME, final int POSX, final int POSY, final int WIDTH) {
        super(NAME, POSX, POSY, WIDTH);
    }
    
    protected PanelBaseClient(final String NAME, final int POSX, final int POSY) {
        super(NAME, POSX, POSY);
    }
    
    @Override
    public abstract void addElements();
    
    @Override
    public void drawScreen(final int POSX, final int POSY) {
        this.updatePanel(POSX, POSY);
        GuiUtils.drawBorderedRect(this.getPosX(), this.getPosY(), this.getPosX() + this.getWidth(), this.getOpen() ? (this.getPosY() + this.getHeight() + 3) : (this.getPosY() + 20), -11184811, -13421773);
        Twelve.getInstance().guiFont.drawString(this.getName().toUpperCase(), this.getPosX() + 5, this.getPosY() + 7, NahrFont.FontType.PLAIN, this.getPin() ? -44976 : -36752, 0);
        this.addElements();
        this.drawElements(POSX, POSY);
    }
}
