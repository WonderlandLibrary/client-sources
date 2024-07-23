package io.github.liticane.monoxide.module.impl.hud;

import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.api.data.ModuleData;
import io.github.liticane.monoxide.module.api.ModuleCategory;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.theme.data.enums.ElementType;
import io.github.liticane.monoxide.theme.impl.element.ModuleListElement;
import io.github.liticane.monoxide.theme.impl.element.modulelist.CustomModuleList;
import io.github.liticane.monoxide.theme.storage.ThemeStorage;
import io.github.liticane.monoxide.value.impl.MultiBooleanValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.listener.event.client.module.DisableModuleEvent;
import io.github.liticane.monoxide.listener.event.client.module.EnableModuleEvent;
import io.github.liticane.monoxide.listener.event.minecraft.render.Render2DEvent;
import io.github.liticane.monoxide.listener.radbus.Listen;
import io.github.liticane.monoxide.util.interfaces.ColorPalette;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import net.minecraft.client.gui.FontRenderer;
import io.github.liticane.monoxide.module.impl.hud.clientOverlay.IClientOverlayComponent;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

@ModuleData(name = "ModuleList", description = "Shows a list of enabled modules", category = ModuleCategory.HUD)
public class ModuleListModule extends Module implements ColorPalette, IClientOverlayComponent {
    public ModeValue moduleListMode = new ModeValue("Module List Mode", this, new String[]{"Custom"});
    private MultiBooleanValue ignoredCategories = new MultiBooleanValue("Hide Categories", this, new String[] {"Hud", "Render"}, new String[] {"Hud", "Render", "Movement", "Combat", "Player", "Others"});
    private LinkedHashMap<Module, DecelerateAnimation> moduleHashMap = new LinkedHashMap<>();

    @Override
    public void draw(Render2DEvent render2DEvent, AtomicFloat leftY, AtomicFloat rightY) {
        ModuleListElement moduleListElement = ThemeStorage.getInstance().getThemeObject(moduleListMode.getValue(), ElementType.MODULE_LIST);
        if(this.isEnabled() && moduleListElement != null) {
            List<Module> modulesToShow = new ArrayList<>();

            for (Module module : ModuleManager.getInstance()) {
                ModuleCategory moduleCategory = module.getCategory();
                if (!isIgnoredCategory(moduleCategory)) {
                    modulesToShow.add(module);
                }
            }

            for (Module module : modulesToShow) {
                if (!moduleHashMap.containsKey(module)) {
                    if(moduleListElement.shouldAnimate()) {
                        moduleHashMap.put(module, new DecelerateAnimation(200, 1, module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS));
                    } else {
                        moduleHashMap.put(module, new DecelerateAnimation(1, 1, module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS));
                    }
                }
            }

            List<Module> sortedModules = new ArrayList<>(modulesToShow);
            Collections.sort(sortedModules, (mod1, mod2) -> {
                FontRenderer fontRenderer = moduleListElement.getFontRenderer();
                String name1 = moduleListMode.is("Custom") ? ((CustomModuleList) moduleListElement).getModuleName(mod1, false) : mod1.getName();
                String name2 = moduleListMode.is("Custom") ? ((CustomModuleList) moduleListElement).getModuleName(mod2, false) : mod2.getName();
                return fontRenderer.getStringWidthInt(name2) - fontRenderer.getStringWidthInt(name1);
            });
            LinkedHashMap<Module, DecelerateAnimation> sortedMap = new LinkedHashMap<>();
            for (Module module : sortedModules) {
                sortedMap.put(module, moduleHashMap.get(module));
            }
            moduleHashMap = sortedMap;

            moduleListElement.onDraw(render2DEvent.getScaledResolution(), render2DEvent.getPartialTicks(), leftY, rightY, moduleHashMap);
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Listen
    public final void onModuleEnable(EnableModuleEvent enableModuleEvent) {
        if (enableModuleEvent.getType() == EnableModuleEvent.Type.PRE)
            return;

        ModuleListElement moduleListElement = ThemeStorage.getInstance().getThemeObject(moduleListMode.getValue(), ElementType.MODULE_LIST);

        if(moduleListElement != null) {
            if (this.moduleHashMap.containsKey(enableModuleEvent.getModule())) {
                this.moduleHashMap.get(enableModuleEvent.getModule()).setDirection(Direction.FORWARDS);
            } else {
                if(moduleListElement.shouldAnimate()) {
                    moduleHashMap.put(enableModuleEvent.getModule(), new DecelerateAnimation(200, 1, Direction.FORWARDS));
                } else {
                    moduleHashMap.put(enableModuleEvent.getModule(), new DecelerateAnimation(1, 1, Direction.FORWARDS));
                }
            }
        }
    }

    @Listen
    public final void onModuleDisable(DisableModuleEvent disableModuleEvent) {
        if (disableModuleEvent.getType() == DisableModuleEvent.Type.PRE)
            return;

        ModuleListElement moduleListElement = ThemeStorage.getInstance().getThemeObject(moduleListMode.getValue(), ElementType.MODULE_LIST);

        if(moduleListElement != null) {
            if (this.moduleHashMap.containsKey(disableModuleEvent.getModule())) {
                this.moduleHashMap.get(disableModuleEvent.getModule()).setDirection(Direction.BACKWARDS);
            } else {
                if(moduleListElement.shouldAnimate()) {
                    moduleHashMap.put(disableModuleEvent.getModule(), new DecelerateAnimation(200, 1, Direction.BACKWARDS));
                } else {
                    moduleHashMap.put(disableModuleEvent.getModule(), new DecelerateAnimation(1, 1, Direction.BACKWARDS));
                }
            }
        }

    }

    private boolean isIgnoredCategory(ModuleCategory moduleCategory) {
        String ignoredCategory = ignoredCategories.getValueAsString().toUpperCase();
        return ignoredCategory.contains(moduleCategory.name());
    }

}
