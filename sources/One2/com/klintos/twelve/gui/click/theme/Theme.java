// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.theme;

import java.util.Iterator;
import com.klintos.twelve.gui.click.theme.manager.ThemeManager;
import com.klintos.twelve.gui.click.elements.ElementBase;
import com.klintos.twelve.gui.click.panel.PanelBase;

public class Theme
{
    private String THEME_NAME;
    private boolean THEME_STATE;
    private PanelBase THEME_PANEL;
    private ElementBase THEME_BUTTON;
    private ElementBase THEME_COMBOBOX;
    
    public Theme(final boolean STATE, final String NAME) {
        this.THEME_STATE = STATE;
        this.THEME_NAME = NAME;
    }
    
    public String getName() {
        return this.THEME_NAME;
    }
    
    public void setName(final String NAME) {
        this.THEME_NAME = NAME;
    }
    
    public boolean getState() {
        return this.THEME_STATE;
    }
    
    public void setState(final boolean STATE) {
        this.THEME_STATE = STATE;
        if (this.getState()) {
            for (final Theme THEME : ThemeManager.getInstance().getList()) {
                if (THEME != this) {
                    THEME.setState(false);
                }
            }
        }
        else if (!this.getState() && ThemeManager.getInstance().getCurrentTheme() == null) {
            ThemeManager.getInstance().getTheme("Pure").setState(true);
        }
    }
    
    public PanelBase getPanel() {
        return this.THEME_PANEL;
    }
    
    public void setPanel(final PanelBase PANEL) {
        this.THEME_PANEL = PANEL;
    }
    
    public ElementBase getButton() {
        return this.THEME_BUTTON;
    }
    
    public void setButton(final ElementBase BUTTON) {
        this.THEME_BUTTON = BUTTON;
    }
    
    public ElementBase getComboBox() {
        return this.THEME_COMBOBOX;
    }
    
    public void setComboBox(final ElementBase BUTTON) {
        this.THEME_COMBOBOX = BUTTON;
    }
}
