// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.gui.ui;

import me.chrest.client.module.ModuleManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import me.chrest.utils.RenderingUtils;
import me.chrest.utils.Rainbow;
import me.chrest.client.module.modules.render.Hud;
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
    public static double mainhue;
    
    static {
        TabGui.section = Section.CATEGORY;
        TabGui.categoryTab = 0;
        TabGui.modTab = 0;
        TabGui.categoryPosition = 15;
        TabGui.categoryTargetPosition = 15;
        TabGui.modPosition = 15;
        TabGui.modTargetPosition = 15;
        TabGui.mainhue = 0.0;
    }
    
    public static void init() {
        int highestWidth = 0;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category category = values[i];
            final String name = capitalize(category.name().toLowerCase());
            final int stringWidth = ClientUtils.clientFont.getStringWidth(name);
            highestWidth = Math.max(stringWidth, highestWidth);
        }
        TabGui.baseCategoryWidth = highestWidth;
        TabGui.baseCategoryHeight = Module.Category.values().length * 14 + 2;
    }
    
    public static void render() {
        int index = 0;
        updateBars();
        final Hud hudModule = (Hud)new Hud().getInstance();
        RenderingUtils.drawBorderedRect(2, 14, TabGui.baseCategoryWidth + 6, TabGui.baseCategoryHeight + 1, -1610612736, Rainbow.rainbow(index * 200000000L, 1.0f).getRGB());
        RenderingUtils.drawGradientRect(3.0f, TabGui.categoryPosition, TabGui.baseCategoryWidth + 5, TabGui.categoryPosition + 11, -13421773, Rainbow.rainbow(index * 200000000L, 1.0f).getRGB());
        int yPos = 13;
        int yPosBottom = 29;
        for (int i = 0; i < Module.Category.values().length; ++i) {
            final Module.Category category = Module.Category.values()[i];
            final String name = capitalize(category.name().toLowerCase());
            ClientUtils.clientFont.drawStringWithShadow(name, TabGui.baseCategoryWidth / ClientUtils.clientFont.getStringWidth(name) / 2 + 4, yPos + 3, -1);
            yPos += 12;
            yPosBottom += 12;
        }
        if (TabGui.section == Section.MODS) {
            RenderingUtils.drawBorderedRect(TabGui.baseCategoryWidth + 6, TabGui.categoryPosition - 1, TabGui.baseCategoryWidth + 2 + TabGui.baseModWidth, TabGui.categoryPosition + getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() * 12, -1610612736, Rainbow.rainbow(index * 200000000L, 1.0f).getRGB());
            RenderingUtils.drawGradientRect(TabGui.baseCategoryWidth + 7, TabGui.modPosition, TabGui.baseCategoryWidth + TabGui.baseModWidth + 1, TabGui.modPosition + 11, -13421773, Rainbow.rainbow(index * 200000000L, 1.0f).getRGB());
            yPos = TabGui.categoryPosition;
            yPosBottom = TabGui.categoryPosition + 12;
            for (int i = 0; i < getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size(); ++i) {
                final Module mod = getModsInCategory(Module.Category.values()[TabGui.categoryTab]).get(i);
                final String name = mod.getDisplayName();
                ClientUtils.clientFont.drawStringWithShadow(name, TabGui.baseCategoryWidth + TabGui.baseModWidth / ClientUtils.clientFont.getStringWidth(name) / 2 + 8, yPos + 1, mod.isEnabled() ? -1 : -4210753);
                yPos += 12;
                yPosBottom += 12;
                ++index;
            }
        }
    }
    
    public static void keyPress(final int key) {
        if (TabGui.section == Section.CATEGORY) {
            switch (key) {
                case 205: {
                    int highestWidth = 0;
                    for (final Module module : getModsInCategory(Module.Category.values()[TabGui.categoryTab])) {
                        final String name = capitalize(module.getDisplayName().toLowerCase());
                        final int stringWidth = ClientUtils.clientFont.getStringWidth(name);
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
                    TabGui.categoryTargetPosition += 12;
                    if (TabGui.categoryTab <= Module.Category.values().length - 1) {
                        break;
                    }
                    TabGui.transitionQuickly = true;
                    TabGui.categoryTargetPosition = 15;
                    TabGui.categoryTab = 0;
                    break;
                }
                case 200: {
                    --TabGui.categoryTab;
                    TabGui.categoryTargetPosition -= 12;
                    if (TabGui.categoryTab >= 0) {
                        break;
                    }
                    TabGui.transitionQuickly = true;
                    TabGui.categoryTargetPosition = 15 + (Module.Category.values().length - 1) * 12;
                    TabGui.categoryTab = Module.Category.values().length - 1;
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
                    TabGui.modTargetPosition += 12;
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
                    TabGui.modTargetPosition -= 12;
                    if (TabGui.modTab < 0) {
                        TabGui.transitionQuickly = true;
                        TabGui.modTargetPosition = TabGui.categoryTargetPosition + (getModsInCategory(Module.Category.values()[TabGui.categoryTab]).size() - 1) * 12;
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
        int increment = TabGui.transitionQuickly ? 100 : 20;
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
    
    public static String capitalize(final String line) {
        return String.valueOf(Character.toUpperCase(line.charAt(0))) + line.substring(1);
    }
    
    private enum Section
    {
        CATEGORY("CATEGORY", 0, "CATEGORY", 0), 
        MODS("MODS", 1, "MODS", 1);
        
        private Section(final String s2, final int n2, final String s, final int n) {
        }
    }
}
