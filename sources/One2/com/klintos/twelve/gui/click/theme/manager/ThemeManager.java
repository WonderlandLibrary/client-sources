// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.click.theme.manager;

import java.util.Iterator;
import java.util.ArrayList;
import com.klintos.twelve.gui.click.theme.Theme;
import java.util.List;

public class ThemeManager
{
    private static List<Theme> THEME_LIST;
    private static List<String> THEME_NAMELIST;
    
    static {
        ThemeManager.THEME_LIST = new ArrayList<Theme>();
        ThemeManager.THEME_NAMELIST = new ArrayList<String>();
    }
    
    public List<Theme> getList() {
        return ThemeManager.THEME_LIST;
    }
    
    public List<String> getNameList() {
        return ThemeManager.THEME_NAMELIST;
    }
    
    public void addTheme(final Theme THEME) {
        ThemeManager.THEME_LIST.add(THEME);
        ThemeManager.THEME_NAMELIST.add(THEME.getName());
    }
    
    public void removeTheme(final Theme THEME) {
        ThemeManager.THEME_LIST.remove(THEME);
        ThemeManager.THEME_NAMELIST.remove(THEME.getName());
    }
    
    public Theme getCurrentTheme() {
        for (final Theme THEME : this.getList()) {
            if (THEME.getState()) {
                return THEME;
            }
        }
        return null;
    }
    
    public Theme getTheme(final String NAME) {
        for (final Theme THEME : this.getList()) {
            if ((THEME.getName().equalsIgnoreCase(NAME) || THEME.getName().trim().equalsIgnoreCase(NAME)) && THEME != null) {
                return THEME;
            }
        }
        return null;
    }
    
    public static ThemeManager getInstance() {
        return new ThemeManager();
    }
}
