/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render.hudkirkax.tabgui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.module.modules.render.hudkirkax.tabgui.TabGui;
import me.thekirkayt.event.events.KeyPressEvent;
import me.thekirkayt.event.events.Render2DEvent;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import org.apache.commons.lang3.StringUtils;

public class NewTabGui
extends TabGui {
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

    public NewTabGui(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean setupSizes() {
        int highestWidth = 0;
        for (Module.Category category : Module.Category.values()) {
            String name = StringUtils.capitalize((String)category.name().toLowerCase());
            int stringWidth = ClientUtils.clientFont().getStringWidth(name);
            highestWidth = Math.max(stringWidth, highestWidth);
        }
        baseCategoryWidth = highestWidth + 6;
        baseCategoryHeight = Module.Category.values().length * 14 + 2;
        return true;
    }

    @Override
    public boolean onRender(Render2DEvent event) {
        if (super.onRender(event)) {
            int i;
            String name;
            NewTabGui.updateBars();
            RenderUtils.rectangle(2.0, 14.0, 2 + baseCategoryWidth, 14 + baseCategoryHeight, -1610612736);
            RenderUtils.rectangle(3.0, categoryPosition, 2 + baseCategoryWidth - 1, categoryPosition + 14, -11184641);
            int yPos = 15;
            int yPosBottom = 29;
            for (i = 0; i < Module.Category.values().length; ++i) {
                Module.Category category = Module.Category.values()[i];
                name = StringUtils.capitalize((String)category.name().toLowerCase());
                ClientUtils.clientFont().drawStringWithShadow(name, baseCategoryWidth / 2 - ClientUtils.clientFont().getStringWidth(name) / 2 + 3, yPos + 3, categoryTab == i ? -1 : -6052957);
                yPos += 14;
                yPosBottom += 14;
            }
            if (section == Section.MODS) {
                RenderUtils.rectangle(baseCategoryWidth + 4, categoryPosition - 1, baseCategoryWidth + 2 + baseModWidth, categoryPosition + NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() * 14 + 1, -1610612736);
                RenderUtils.rectangle(baseCategoryWidth + 5, modPosition, baseCategoryWidth + baseModWidth + 1, modPosition + 14, -11184641);
                yPos = categoryPosition;
                yPosBottom = categoryPosition + 14;
                for (i = 0; i < NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).size(); ++i) {
                    Module mod = NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).get(i);
                    name = mod.getDisplayName();
                    ClientUtils.clientFont().drawStringWithShadow(name, baseCategoryWidth + baseModWidth / 2 - ClientUtils.clientFont().getStringWidth(name) / 2 + 3, yPos + 3, modTab == i ? (mod.isEnabled() ? -1 : -3026479) : (mod.isEnabled() ? -11184641 : -6052957));
                    yPos += 14;
                    yPosBottom += 14;
                }
            }
        }
        return true;
    }

    @Override
    public boolean onKeypress(KeyPressEvent event) {
        block17 : {
            int key;
            block18 : {
                if (!super.onKeypress(event)) break block17;
                key = event.getKey();
                if (section != Section.CATEGORY) break block18;
                switch (key) {
                    case 205: {
                        int highestWidth = 0;
                        for (Module module : NewTabGui.getModsInCategory(Module.Category.values()[categoryTab])) {
                            String name = StringUtils.capitalize((String)module.getDisplayName().toLowerCase());
                            int stringWidth = ClientUtils.clientFont().getStringWidth(name);
                            highestWidth = Math.max(stringWidth, highestWidth);
                        }
                        baseModWidth = highestWidth + 6;
                        baseModHeight = NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() * 14 + 2;
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
                        break block17;
                    }
                    case 200: {
                        categoryTargetPosition -= 14;
                        if (--categoryTab >= 0) break block17;
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
                break block17;
            }
            if (section == Section.MODS) {
                switch (key) {
                    case 203: {
                        section = Section.CATEGORY;
                        break;
                    }
                    case 205: {
                        Module mod = NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).get(modTab);
                        mod.toggle();
                        break;
                    }
                    case 208: {
                        modTargetPosition += 14;
                        if (++modTab <= NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() - 1) break;
                        transitionQuickly = true;
                        modTargetPosition = categoryTargetPosition;
                        modTab = 0;
                        break;
                    }
                    case 200: {
                        modTargetPosition -= 14;
                        if (--modTab >= 0) break;
                        transitionQuickly = true;
                        modTargetPosition = categoryTargetPosition + (NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() - 1) * 14;
                        modTab = NewTabGui.getModsInCategory(Module.Category.values()[categoryTab]).size() - 1;
                    }
                }
            }
        }
        return true;
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
        for (Module mod : ModuleManager.getModules()) {
            if (mod.getCategory() != category) continue;
            modList.add(mod);
        }
        modList.sort(new Comparator<Module>(){

            @Override
            public int compare(Module m1, Module m2) {
                String s1 = m1.getDisplayName();
                String s2 = m2.getDisplayName();
                return s1.compareTo(s2);
            }
        });
        return modList;
    }

    private static enum Section {
        CATEGORY,
        MODS;
        
    }

}

