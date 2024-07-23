package io.github.liticane.monoxide.theme.impl.element.modulelist;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.theme.data.ThemeObjectInfo;
import io.github.liticane.monoxide.theme.data.enums.ElementType;
import io.github.liticane.monoxide.theme.data.enums.ThemeObjectType;
import io.github.liticane.monoxide.theme.impl.element.ModuleListElement;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import io.github.liticane.monoxide.util.render.shader.render.ingame.RenderableShaders;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;
import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Ryu", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class RyuModuleList extends ModuleListElement {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleHashMap) {
        if (leftY.get() == 0)
            leftY.set(6);
        FontRenderer roboto17 = FontStorage.getInstance().findFont("SF UI Medium", 17);
        // This is disgusting
        RenderableShaders.renderAndRun(false, true, () -> {
            float moduleY = leftY.get();
            for (Module module : moduleHashMap.keySet()) {
                float moduleHeight = roboto17.FONT_HEIGHT + 3;
                if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                    String name = module.getName();
                    float rectWidth = (roboto17.getStringWidthInt(name) + 4);
                    float moduleX = 2 - rectWidth + (float) (moduleHashMap.get(module).getOutput() * rectWidth);
                    RenderUtil.drawRect(moduleX, moduleY, rectWidth, moduleHeight, new Color(0, 0, 0, 80).getRGB());
                    moduleY += moduleHeight;
                }
            }
        });
        RenderableShaders.renderAndRun(true, false, () -> {
            float moduleY = leftY.get();
            for (Module module : moduleHashMap.keySet()) {
                float moduleHeight = roboto17.FONT_HEIGHT + 3;
                if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                    String name = module.getName();
                    float rectWidth = (roboto17.getStringWidthInt(name) + 4);
                    float moduleX = 2 - rectWidth + (float) (moduleHashMap.get(module).getOutput() * rectWidth);
                    FontStorage.getInstance().findFont("SF UI", 17).drawTotalCenteredString(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 + 0.5f, new Color(0, 0, 0).getRGB());
                    moduleY += moduleHeight;
                }
            }
        });
        float moduleY = leftY.get();
        for (Module module : moduleHashMap.keySet()) {
            float moduleHeight = roboto17.FONT_HEIGHT + 3;
            if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                String name = module.getName();
                float rectWidth = (roboto17.getStringWidthInt(name) + 4);
                float moduleX = 2 - rectWidth + (float) (moduleHashMap.get(module).getOutput() * rectWidth);
                roboto17.drawTotalCenteredString(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 + 0.5f, -1);
                moduleY += moduleHeight;
            }
        }
    }

    @Override
    public boolean shouldAnimate() {
        return true;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return FontStorage.getInstance().findFont("SF UI Medium", 17);
    }

}
