package me.satisfactory.base.gui.tabgui;

import net.minecraft.client.*;
import me.mees.remix.font.*;
import me.satisfactory.base.*;
import net.minecraft.client.gui.*;
import java.awt.*;
import me.satisfactory.base.module.*;
import java.util.*;

public class TabGUI
{
    public static int baseCategoryWidth;
    private static int baseCategoryHeight;
    private static int baseModWidth;
    private static Section section;
    private static int categoryTab;
    private static int modTab;
    private static int categoryPosition;
    private static int categoryTargetPosition;
    private static int modPosition;
    private static int modTargetPosition;
    private static boolean transitionQuickly;
    private static long lastUpdateTime;
    
    public static void init() {
        int highestWidth = 0;
        for (final Category category : Category.values()) {
            if (category != Category.HIDDEN) {
                final String name = category.name();
                final int stringWidth = getStringWidth(name) + 5;
                highestWidth = Math.max(stringWidth, highestWidth);
            }
        }
        TabGUI.baseCategoryWidth = highestWidth + 18;
        TabGUI.baseCategoryHeight = (Category.values().length - 1) * 15 - 7;
    }
    
    public static void renderString(final String string, final int x, final int y, final int color) {
        Minecraft.getMinecraft().fontRendererObj.drawString(string, (float)x, y - 1.0f, color);
    }
    
    public static int getStringWidth(final String string) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
    }
    
    public static void render(final CFontRenderer fu) {
        final boolean shouldRemixLogo = Base.INSTANCE.getSettingManager().getSettingByName("Logo").booleanValue();
        final int extraX = 8;
        final int extraY = 69;
        updateBars();
        if (Base.INSTANCE.getModuleManager().getModByName("HUD").getMode().getName().equalsIgnoreCase("Remix")) {
            Gui.drawRoundedRect((float)(1 + (shouldRemixLogo ? extraX : 0)), (float)(2 + (shouldRemixLogo ? extraY : 0)), (float)(2 + TabGUI.baseCategoryWidth + (shouldRemixLogo ? extraX : 0)), (float)(5 + TabGUI.baseCategoryHeight + (shouldRemixLogo ? extraY : 0)), 3.0f, -1610612736);
            Gui.drawRect(3 + (shouldRemixLogo ? extraX : 0), TabGUI.categoryPosition - 1 + 3 + (shouldRemixLogo ? extraY : 0) - 11, 4 + (shouldRemixLogo ? extraX : 0), TabGUI.categoryPosition + 15 - 3 + (shouldRemixLogo ? extraY : 0) - 11, Base.INSTANCE.GetMainColor());
        }
        else {
            Gui.drawRect(1 + (shouldRemixLogo ? extraX : 0), 2 + (shouldRemixLogo ? extraY : 0), 1 + TabGUI.baseCategoryWidth + (shouldRemixLogo ? extraX : 0), 5 + TabGUI.baseCategoryHeight + (shouldRemixLogo ? extraY : 0), -1610612736);
            Gui.drawRect(1 + (shouldRemixLogo ? extraX : 0), TabGUI.categoryPosition - 1 + (shouldRemixLogo ? extraY : 0) - 11, 1 + TabGUI.baseCategoryWidth + (shouldRemixLogo ? extraX : 0), TabGUI.categoryPosition + 15 + (shouldRemixLogo ? extraY : 0) - 11, new Color(0, 0, 0, 50).getRGB());
        }
        float yPos = 15.0f;
        for (final Category category : Category.values()) {
            if (category != Category.HIDDEN) {
                final String name1 = category.name().toLowerCase();
                final String name2 = name1.substring(0, 1).toUpperCase() + name1.substring(1);
                if (category.equals(Category.values()[TabGUI.categoryTab])) {
                    fu.drawString(name2, (float)(8 + (shouldRemixLogo ? extraX : 0)), yPos + (shouldRemixLogo ? extraY : 0) - fu.getStringHeight(name2), -1);
                }
                else {
                    fu.drawString(name2, (float)(5 + (shouldRemixLogo ? extraX : 0)), yPos + (shouldRemixLogo ? extraY : 0) - fu.getStringHeight(name2), -1);
                }
                yPos += 14.0f;
            }
        }
        if (TabGUI.section == Section.MODS) {
            if (Base.INSTANCE.getModuleManager().getModByName("HUD").getMode().getName().equalsIgnoreCase("Remix")) {
                Gui.drawRoundedRect((float)(TabGUI.baseCategoryWidth + 4 + (shouldRemixLogo ? extraX : 0)), (float)(TabGUI.categoryPosition - 1 + (shouldRemixLogo ? extraY : 0) - 11), (float)(TabGUI.baseCategoryWidth + TabGUI.baseModWidth + 10 + (shouldRemixLogo ? extraX : 0)), (float)(TabGUI.categoryPosition + getModsInCategory(Category.values()[TabGUI.categoryTab]).size() * 14 + 1 + (shouldRemixLogo ? extraY : 0) - 11), 3.0f, -1610612736);
                Gui.drawRect(TabGUI.baseCategoryWidth + 3 + 4 + (shouldRemixLogo ? extraX : 0), TabGUI.modPosition - 1 + (shouldRemixLogo ? extraY : 0) + 3 - 11, TabGUI.baseCategoryWidth + 3 + 4 + 1 + (shouldRemixLogo ? extraX : 0), TabGUI.modPosition + 15 + (shouldRemixLogo ? extraY : 0) - 3 - 11, Base.INSTANCE.GetMainColor());
            }
            else {
                Gui.drawRect(TabGUI.baseCategoryWidth + 4 + (shouldRemixLogo ? extraX : 0), TabGUI.categoryPosition - 1 + (shouldRemixLogo ? extraY : 0) - 11, TabGUI.baseCategoryWidth + 4 + TabGUI.baseModWidth + 10 + (shouldRemixLogo ? extraX : 0), TabGUI.categoryPosition + getModsInCategory(Category.values()[TabGUI.categoryTab]).size() * 14 + 1 + (shouldRemixLogo ? extraY : 0) - 11, -1610612736);
                Gui.drawRect(TabGUI.baseCategoryWidth + 4 + (shouldRemixLogo ? extraX : 0), TabGUI.modPosition - 1 + (shouldRemixLogo ? extraY : 0) - 11, TabGUI.baseCategoryWidth + 4 + TabGUI.baseModWidth + 10 + (shouldRemixLogo ? extraX : 0), TabGUI.modPosition + 14 + (shouldRemixLogo ? extraY : 0) + 1 - 11, new Color(0, 0, 0, 50).getRGB());
            }
            yPos = (float)(TabGUI.categoryPosition + 3);
            for (int i = 0; i < getModsInCategory(Category.values()[TabGUI.categoryTab]).size(); ++i) {
                final Module mod = getModsInCategory(Category.values()[TabGUI.categoryTab]).get(i);
                final String name3 = mod.getName();
                if (getModsInCategory(Category.values()[TabGUI.categoryTab]).get(TabGUI.modTab).equals(mod)) {
                    fu.drawString(name3, (float)((int)(TabGUI.baseCategoryWidth + 12.0) + (shouldRemixLogo ? extraX : 0)), yPos + (shouldRemixLogo ? extraY : 0) - fu.getStringHeight(name3) - 2.0f, mod.isEnabled() ? -1 : -4210753);
                }
                else {
                    fu.drawString(name3, (float)((int)(TabGUI.baseCategoryWidth + 9.0) + (shouldRemixLogo ? extraX : 0)), yPos + (shouldRemixLogo ? extraY : 0) - fu.getStringHeight(name3) - 2.0f, mod.isEnabled() ? -1 : -4210753);
                }
                yPos += 14.0f;
            }
        }
    }
    
    public static void keyPress(final int key) {
        if (TabGUI.section == Section.CATEGORY) {
            switch (key) {
                case 205: {
                    int highestWidth = 0;
                    for (final Module module : getModsInCategory(Category.values()[TabGUI.categoryTab])) {
                        final String name = module.getName().toLowerCase();
                        final int stringWidth = Minecraft.getMinecraft().fontRendererObj.getStringWidth(name);
                        highestWidth = Math.max(stringWidth, highestWidth);
                    }
                    TabGUI.baseModWidth = highestWidth + 6;
                    TabGUI.modTargetPosition = (TabGUI.modPosition = TabGUI.categoryTargetPosition);
                    TabGUI.modTab = 0;
                    TabGUI.section = Section.MODS;
                    break;
                }
                case 208: {
                    ++TabGUI.categoryTab;
                    TabGUI.categoryTargetPosition += 14;
                    if (TabGUI.categoryTab <= Category.values().length - 2) {
                        break;
                    }
                    TabGUI.transitionQuickly = true;
                    TabGUI.categoryTargetPosition = 14;
                    TabGUI.categoryTab = 0;
                    break;
                }
                case 200: {
                    --TabGUI.categoryTab;
                    TabGUI.categoryTargetPosition -= 14;
                    if (TabGUI.categoryTab >= 0) {
                        break;
                    }
                    TabGUI.transitionQuickly = true;
                    TabGUI.categoryTargetPosition = 14 + (Category.values().length - 2) * 14;
                    TabGUI.categoryTab = Category.values().length - 2;
                    break;
                }
            }
        }
        else if (TabGUI.section == Section.MODS) {
            switch (key) {
                case 203: {
                    TabGUI.section = Section.CATEGORY;
                    break;
                }
                case 205: {
                    final Module mod = getModsInCategory(Category.values()[TabGUI.categoryTab]).get(TabGUI.modTab);
                    mod.toggle();
                    break;
                }
                case 208: {
                    ++TabGUI.modTab;
                    TabGUI.modTargetPosition += 14;
                    if (TabGUI.modTab > getModsInCategory(Category.values()[TabGUI.categoryTab]).size() - 1) {
                        TabGUI.transitionQuickly = true;
                        TabGUI.modTargetPosition = TabGUI.categoryTargetPosition;
                        TabGUI.modTab = 0;
                        break;
                    }
                    break;
                }
                case 200: {
                    --TabGUI.modTab;
                    TabGUI.modTargetPosition -= 14;
                    if (TabGUI.modTab < 0) {
                        TabGUI.transitionQuickly = true;
                        TabGUI.modTargetPosition = TabGUI.categoryTargetPosition + (getModsInCategory(Category.values()[TabGUI.categoryTab]).size() - 1) * 14;
                        TabGUI.modTab = getModsInCategory(Category.values()[TabGUI.categoryTab]).size() - 1;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    public static void updateBars() {
        final long timeDifference = System.currentTimeMillis() - TabGUI.lastUpdateTime;
        TabGUI.lastUpdateTime = System.currentTimeMillis();
        int increment = TabGUI.transitionQuickly ? 100 : 20;
        increment = Math.max(1, Math.round((float)(increment * timeDifference / 100L)));
        if (TabGUI.categoryPosition < TabGUI.categoryTargetPosition) {
            TabGUI.categoryPosition += increment;
            if (TabGUI.categoryPosition >= TabGUI.categoryTargetPosition) {
                TabGUI.categoryPosition = TabGUI.categoryTargetPosition;
                TabGUI.transitionQuickly = false;
            }
        }
        else if (TabGUI.categoryPosition > TabGUI.categoryTargetPosition) {
            TabGUI.categoryPosition -= increment;
            if (TabGUI.categoryPosition <= TabGUI.categoryTargetPosition) {
                TabGUI.categoryPosition = TabGUI.categoryTargetPosition;
                TabGUI.transitionQuickly = false;
            }
        }
        if (TabGUI.modPosition < TabGUI.modTargetPosition) {
            TabGUI.modPosition += increment;
            if (TabGUI.modPosition >= TabGUI.modTargetPosition) {
                TabGUI.modPosition = TabGUI.modTargetPosition;
                TabGUI.transitionQuickly = false;
            }
        }
        else if (TabGUI.modPosition > TabGUI.modTargetPosition) {
            TabGUI.modPosition -= increment;
            if (TabGUI.modPosition <= TabGUI.modTargetPosition) {
                TabGUI.modPosition = TabGUI.modTargetPosition;
                TabGUI.transitionQuickly = false;
            }
        }
    }
    
    public static List<Module> getModsInCategory(final Category Category) {
        final List<Module> modList = new ArrayList<Module>();
        for (final Module mod : getSortedModuleArray()) {
            if (mod.getCategory() == Category && Category != Category.HIDDEN) {
                modList.add(mod);
            }
        }
        return modList;
    }
    
    public static List<Module> getSortedModuleArray() {
        final ArrayList<Object> list2;
        final List<Module> list = (List<Module>)(list2 = new ArrayList<Object>());
        Base.INSTANCE.getModuleManager();
        list2.addAll(ModuleManager.modules.values());
        final String s1;
        final String s2;
        final int cmp;
        list.sort((m1, m2) -> {
            s1 = m1.getName();
            s2 = m2.getName();
            cmp = getStringWidth(s2) - getStringWidth(s1);
            return (cmp != 0) ? cmp : s2.compareTo(s1);
        });
        return list;
    }
    
    static {
        TabGUI.section = Section.CATEGORY;
        TabGUI.categoryTab = 0;
        TabGUI.modTab = 0;
        TabGUI.categoryPosition = 15;
        TabGUI.categoryTargetPosition = 14;
        TabGUI.modPosition = 15;
        TabGUI.modTargetPosition = 14;
    }
    
    private enum Section
    {
        CATEGORY("CATEGORY", 0), 
        MODS("MODS", 1);
        
        private Section(final String s, final int n) {
        }
    }
}
