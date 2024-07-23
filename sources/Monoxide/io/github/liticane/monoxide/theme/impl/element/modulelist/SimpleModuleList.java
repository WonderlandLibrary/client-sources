package io.github.liticane.monoxide.theme.impl.element.modulelist;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.theme.data.ThemeObjectInfo;
import io.github.liticane.monoxide.theme.data.enums.ElementType;
import io.github.liticane.monoxide.theme.data.enums.ThemeObjectType;
import io.github.liticane.monoxide.theme.impl.element.ModuleListElement;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.shader.render.ingame.RenderableShaders;

import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Atani Simple", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class SimpleModuleList extends ModuleListElement {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleHashMap) {
        FontRenderer roboto17 = FontStorage.getInstance().findFont("SF UI", 17);
        RenderableShaders.renderAndRun(() -> {
            if (leftY.get() == 0)
                leftY.set(8);
            float moduleY = leftY.get();
            for (Module module : moduleHashMap.keySet()) {
                float moduleHeight = roboto17.FONT_HEIGHT + 8;
                if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                    String name = module.getName();
                    float rectWidth = roboto17.getStringWidthInt(name) + 10;
                    float moduleX = 10 - (rectWidth + 10) + (float) (moduleHashMap.get(module).getOutput() * (rectWidth + 10));
                    RenderUtil.drawRect(moduleX, moduleY, rectWidth, moduleHeight, BACK_TRANS_180);
                    roboto17.drawTotalCenteredStringWithShadow(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 + 0.5f, -1);
                    moduleY += moduleHeight;
                }
            }
        });
    }

    @Override
    public boolean shouldAnimate() {
        return true;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return FontStorage.getInstance().findFont("SF UI", 17);
    }

}
