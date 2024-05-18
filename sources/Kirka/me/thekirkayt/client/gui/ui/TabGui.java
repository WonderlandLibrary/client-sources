/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.ui;

import java.util.ArrayList;
import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderingUtils;
import me.thekirkayt.utils.minecraft.FontRenderer;

public class TabGui {
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

    static {
        section = Section.CATEGORY;
        categoryTab = 0;
        modTab = 0;
        categoryPosition = 15;
        categoryTargetPosition = 15;
        modPosition = 15;
        modTargetPosition = 15;
    }

    public static void init() {
        int highestWidth = 0;
        for (Module.Category category : Module.Category.values()) {
            String name = TabGui.capitalize(category.name().toLowerCase());
            int stringWidth = ClientUtils.clientFont.getStringWidth(name);
            highestWidth = Math.max(stringWidth, highestWidth);
        }
        baseCategoryWidth = highestWidth + 6;
        baseCategoryHeight = Module.Category.values().length * 14 + 2;
    }

    public static void render() {
        int i;
        String name;
        TabGui.updateBars();
        RenderingUtils.drawRect(2.0f, 14.0f, 2 + baseCategoryWidth, 14 + baseCategoryHeight, -1610612736);
        RenderingUtils.drawGradientRect(3.0f, categoryPosition, 2 + baseCategoryWidth - 1, categoryPosition + 14, -9764816, -255255255);
        int yPos = 15;
        int yPosBottom = 29;
        for (i = 0; i < Module.Category.values().length; ++i) {
            Module.Category category = Module.Category.values()[i];
            name = TabGui.capitalize(category.name().toLowerCase());
            ClientUtils.clientFont.drawStringWithShadow(name, baseCategoryWidth / 2 - ClientUtils.clientFont.getStringWidth(name) / 2 + 3, yPos + 3, -1);
            yPos += 14;
            yPosBottom += 14;
        }
        if (section == Section.MODS) {
            RenderingUtils.drawRect(baseCategoryWidth + 4, categoryPosition - 1, baseCategoryWidth + 2 + baseModWidth, categoryPosition + TabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() * 14 + 1, -1610612736);
            RenderingUtils.drawGradientRect(baseCategoryWidth + 5, modPosition, baseCategoryWidth + baseModWidth + 1, modPosition + 14, -9764816, -255255255);
            yPos = categoryPosition;
            yPosBottom = categoryPosition + 14;
            for (i = 0; i < TabGui.getModsInCategory(Module.Category.values()[categoryTab]).size(); ++i) {
                Module mod = TabGui.getModsInCategory(Module.Category.values()[categoryTab]).get(i);
                name = mod.getDisplayName();
                ClientUtils.clientFont.drawStringWithShadow(name, baseCategoryWidth + baseModWidth / 2 - ClientUtils.clientFont.getStringWidth(name) / 2 + 3, yPos + 3, mod.isEnabled() ? -1 : -4210753);
                yPos += 14;
                yPosBottom += 14;
            }
        }
    }

    public static void keyPress(int key) {
        block18 : {
            block17 : {
                if (section != Section.CATEGORY) break block17;
                switch (key) {
                    case 205: {
                        int highestWidth = 0;
                        for (Module module : TabGui.getModsInCategory(Module.Category.values()[categoryTab])) {
                            String name = TabGui.capitalize(module.getDisplayName().toLowerCase());
                            int stringWidth = ClientUtils.clientFont.getStringWidth(name);
                            highestWidth = Math.max(stringWidth, highestWidth);
                        }
                        baseModWidth = highestWidth + 6;
                        baseModHeight = TabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() * 14 + 2;
                        modTargetPosition = modPosition = categoryTargetPosition;
                        modTab = 0;
                        section = Section.MODS;
                        break;
                    }
                    case 208: {
                        categoryTargetPosition += 14;
                        if (++categoryTab > Module.Category.values().length - 1) {
                            transitionQuickly = true;
                            categoryTargetPosition = 15;
                            categoryTab = 0;
                            break;
                        }
                        break block18;
                    }
                    case 200: {
                        categoryTargetPosition -= 14;
                        if (--categoryTab >= 0) break block18;
                        transitionQuickly = true;
                        categoryTargetPosition = 15 + (Module.Category.values().length - 1) * 14;
                        categoryTab = Module.Category.values().length - 1;
                    }
                    default: {
                        break;
                    }
                    {
                    }
                }
                break block18;
            }
            if (section == Section.MODS) {
                switch (key) {
                    case 203: {
                        section = Section.CATEGORY;
                        break;
                    }
                    case 205: {
                        Module mod = TabGui.getModsInCategory(Module.Category.values()[categoryTab]).get(modTab);
                        mod.toggle();
                        break;
                    }
                    case 208: {
                        modTargetPosition += 14;
                        if (++modTab <= TabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() - 1) break;
                        transitionQuickly = true;
                        modTargetPosition = categoryTargetPosition;
                        modTab = 0;
                        break;
                    }
                    case 200: {
                        modTargetPosition -= 14;
                        if (--modTab >= 0) break;
                        transitionQuickly = true;
                        modTargetPosition = categoryTargetPosition + (TabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() - 1) * 14;
                        modTab = TabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() - 1;
                    }
                }
            }
        }
    }

    private static void updateBars() {
        long timeDifference = System.currentTimeMillis() - lastUpdateTime;
        lastUpdateTime = System.currentTimeMillis();
        int increment = transitionQuickly ? 100 : 20;
        increment = Math.max(1, Math.round((long)increment * timeDifference / 100L));
        if (categoryPosition < categoryTargetPosition) {
            if ((categoryPosition += increment) >= categoryTargetPosition) {
                categoryPosition = categoryTargetPosition;
                transitionQuickly = false;
            }
        } else if (categoryPosition > categoryTargetPosition && (categoryPosition -= increment) <= categoryTargetPosition) {
            categoryPosition = categoryTargetPosition;
            transitionQuickly = false;
        }
        if (modPosition < modTargetPosition) {
            if ((modPosition += increment) >= modTargetPosition) {
                modPosition = modTargetPosition;
                transitionQuickly = false;
            }
        } else if (modPosition > modTargetPosition && (modPosition -= increment) <= modTargetPosition) {
            modPosition = modTargetPosition;
            transitionQuickly = false;
        }
    }

    private static List<Module> getModsInCategory(Module.Category category) {
        ArrayList<Module> modList = new ArrayList<Module>();
        for (Module mod : ModuleManager.moduleList) {
            if (mod.getCategory() != category) continue;
            modList.add(mod);
        }
        return modList;
    }

    public static String capitalize(String line) {
        return String.valueOf(Character.toUpperCase(line.charAt(0))) + line.substring(1);
    }

    private static enum Section {
        CATEGORY("CATEGORY", 0),
        MODS("MODS", 1);
        

        private Section(String s, int n2) {
        }
    }

}

