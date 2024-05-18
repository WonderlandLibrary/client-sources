// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.gui.ui.virtue;

import me.chrest.client.module.ModuleManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import me.chrest.utils.RenderingUtils;
import me.chrest.utils.ClientUtils;
import me.chrest.client.module.Module;

public class TabGui
{
    private static final int NO_COLOR = 0;
    private static final int INSIDE_COLOR = -1610612736;
    private static final int BORDER_COLOR = 2013265920;
    private static final int COMPONENT_HEIGHT = 14;
    private static int baseCategoryWidth;
    private static int baseCategoryHeight;
    private static int baseCategoryWidth2;
    private static int baseCategoryHeight2;
    private static int baseModWidth;
    private static int baseModHeight;
    private static Section section;
    private static int categoryTab;
    private static int modTab;
    private static int categoryPosition;
    private static int categoryTargetPosition;
    private static int modPosition;
    private static int modTargetPosition;
    private static boolean transitionQuickly;
    private static long lastUpdateTime;
    
    static {
        TabGui.section = Section.CATEGORY;
        TabGui.categoryTab = 0;
        TabGui.modTab = 0;
        TabGui.categoryPosition = 15;
        TabGui.categoryTargetPosition = 15;
        TabGui.modPosition = 15;
        TabGui.modTargetPosition = 15;
    }
    
    public static void init() {
        int highestWidth = 0;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category category = values[i];
            final String displayName = me.chrest.client.gui.ui.TabGui.capitalize(category.name().toLowerCase());
            final int stringWidth = ClientUtils.clientFont.getStringWidth(displayName);
            highestWidth = Math.max(stringWidth, highestWidth);
        }
        TabGui.baseCategoryWidth = highestWidth + 6;
        TabGui.baseCategoryHeight = Module.Category.values().length * 14 + 2;
    }
    
    public static void render() {
        updateBars();
        int yPos = 15;
        int yPosBottom = 29;
        for (int i = 0; i < Module.Category.values().length; ++i) {
            final Module.Category category = Module.Category.values()[i];
            final String displayName = me.chrest.client.gui.ui.TabGui.capitalize(category.name().toLowerCase());
            RenderingUtils.drawBorderedRect(2.0f, yPos, 2 + TabGui.baseCategoryWidth, yPos + 18.5f, 0.5f, -13224394, -16777216);
            RenderingUtils.drawBorderedRect(2.0f, TabGui.categoryPosition, 2 + TabGui.baseCategoryWidth, TabGui.categoryPosition + 18.5f, 0.5f, -15066598, -16777216);
            yPos += 20;
            yPosBottom += 14;
        }
        int cPos = 15;
        for (int j = 0; j < Module.Category.values().length; ++j) {
            final Module.Category category2 = Module.Category.values()[j];
            final String displayName2 = me.chrest.client.gui.ui.TabGui.capitalize(category2.name().toLowerCase());
            ClientUtils.clientFont.drawStringWithShadow(displayName2, TabGui.baseCategoryWidth / 2 - ClientUtils.clientFont.getStringWidth(displayName2) / 2 + 3, cPos + 5, -4210753);
            cPos += 20;
            yPosBottom += 14;
        }
        if (TabGui.section == Section.MODS) {
            yPos = TabGui.categoryPosition;
            yPosBottom = TabGui.categoryPosition + 14;
            for (int j = 0; j < getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size(); ++j) {
                RenderingUtils.drawBorderedRect(TabGui.baseCategoryWidth + 4, yPos, TabGui.baseCategoryWidth + 2 + TabGui.baseModWidth, yPos + 18.5f, 0.5f, -13224394, -16777216);
                RenderingUtils.drawBorderedRect(TabGui.baseCategoryWidth + 4, TabGui.modPosition, TabGui.baseCategoryWidth + TabGui.baseModWidth + 2, TabGui.modPosition + 18.5f, 0.5f, -15066598, -16777216);
                yPos += 20;
                yPosBottom += 14;
            }
            cPos = TabGui.categoryPosition;
            for (int j = 0; j < getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size(); ++j) {
                final Module mod = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).get(j);
                final String displayName2 = mod.getDisplayName();
                ClientUtils.clientFont.drawStringWithShadow(displayName2, TabGui.baseCategoryWidth + TabGui.baseModWidth / 2 - ClientUtils.clientFont.getStringWidth(displayName2) / 2 + 3, cPos + 5, mod.isEnabled() ? -1 : -4210753);
                cPos += 20;
            }
        }
    }
    
    public static void keyPress(final int key) {
        if (TabGui.section == Section.CATEGORY) {
            switch (key) {
                case 205: {
                    int highestWidth = 0;
                    for (final Module module : getModsInCategory(Module.Category.values()[TabGui.categoryTab])) {
                        final String displayName = me.chrest.client.gui.ui.TabGui.capitalize(module.getDisplayName().toLowerCase());
                        final int stringWidth = ClientUtils.clientFont.getStringWidth(displayName);
                        highestWidth = Math.max(stringWidth, highestWidth);
                    }
                    TabGui.baseModWidth = highestWidth + 6;
                    TabGui.baseModHeight = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() * 14 + 2;
                    TabGui.modTargetPosition = (TabGui.modPosition = TabGui.categoryTargetPosition);
                    TabGui.modTab = 0;
                    TabGui.section = Section.MODS;
                    break;
                }
                case 208: {
                    ++TabGui.categoryTab;
                    TabGui.categoryTargetPosition += 20;
                    if (TabGui.categoryTab > Module.Category.values().length - 1) {
                        TabGui.transitionQuickly = true;
                        TabGui.categoryTargetPosition = 15;
                        TabGui.categoryTab = 0;
                        break;
                    }
                    break;
                }
                case 200: {
                    --TabGui.categoryTab;
                    TabGui.categoryTargetPosition -= 20;
                    if (TabGui.categoryTab < 0) {
                        TabGui.transitionQuickly = true;
                        TabGui.categoryTargetPosition = 15 + (Module.Category.values().length - 1) * 20;
                        TabGui.categoryTab = Module.Category.values().length - 1;
                        break;
                    }
                    break;
                }
            }
        }
        else if (TabGui.section == Section.MODS) {
            switch (key) {
                case 203: {
                    TabGui.section = Section.CATEGORY;
                    break;
                }
                case 205: {
                    final Module mod = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).get(TabGui.modTab);
                    mod.toggle();
                    break;
                }
                case 208: {
                    ++TabGui.modTab;
                    TabGui.modTargetPosition += 20;
                    if (TabGui.modTab > getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() - 1) {
                        TabGui.transitionQuickly = true;
                        TabGui.modTargetPosition = TabGui.categoryTargetPosition;
                        TabGui.modTab = 0;
                        break;
                    }
                    break;
                }
                case 200: {
                    --TabGui.modTab;
                    TabGui.modTargetPosition -= 20;
                    if (TabGui.modTab < 0) {
                        TabGui.transitionQuickly = true;
                        TabGui.modTargetPosition = TabGui.categoryTargetPosition + (getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() - 1) * 20;
                        TabGui.modTab = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() - 1;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    private static void updateBars() {
        final long timeDifference = System.currentTimeMillis() - TabGui.lastUpdateTime;
        TabGui.lastUpdateTime = System.currentTimeMillis();
        int increment = TabGui.transitionQuickly ? 10000 : 10000;
        increment = Math.max(1, Math.round(increment * timeDifference / 100L));
        if (TabGui.categoryPosition < TabGui.categoryTargetPosition) {
            TabGui.categoryPosition += increment;
            if (TabGui.categoryPosition >= TabGui.categoryTargetPosition) {
                TabGui.categoryPosition = TabGui.categoryTargetPosition;
                TabGui.transitionQuickly = false;
            }
        }
        else if (TabGui.categoryPosition > TabGui.categoryTargetPosition) {
            TabGui.categoryPosition -= increment;
            if (TabGui.categoryPosition <= TabGui.categoryTargetPosition) {
                TabGui.categoryPosition = TabGui.categoryTargetPosition;
                TabGui.transitionQuickly = false;
            }
        }
        if (TabGui.modPosition < TabGui.modTargetPosition) {
            TabGui.modPosition += increment;
            if (TabGui.modPosition >= TabGui.modTargetPosition) {
                TabGui.modPosition = TabGui.modTargetPosition;
                TabGui.transitionQuickly = false;
            }
        }
        else if (TabGui.modPosition > TabGui.modTargetPosition) {
            TabGui.modPosition -= increment;
            if (TabGui.modPosition <= TabGui.modTargetPosition) {
                TabGui.modPosition = TabGui.modTargetPosition;
                TabGui.transitionQuickly = false;
            }
        }
    }
    
    private static List<Module> getModsInCategory(final Module.Category category) {
        final List<Module> modList = new ArrayList<Module>();
        for (final Module mod : ModuleManager.moduleList) {
            if (mod.getCategory() == category) {
                modList.add(mod);
            }
        }
        return modList;
    }
    
    private enum Section
    {
        CATEGORY("CATEGORY", 0, "CATEGORY", 0), 
        MODS("MODS", 1, "MODS", 1);
        
        private Section(final String s2, final int n2, final String s, final int n) {
        }
    }
}
