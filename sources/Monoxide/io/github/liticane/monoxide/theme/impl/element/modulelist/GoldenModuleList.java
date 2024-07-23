package io.github.liticane.monoxide.theme.impl.element.modulelist;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.theme.data.ThemeObjectInfo;
import io.github.liticane.monoxide.theme.data.enums.ElementType;
import io.github.liticane.monoxide.theme.data.enums.ThemeObjectType;
import io.github.liticane.monoxide.theme.impl.element.ModuleListElement;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import io.github.liticane.monoxide.util.render.shader.render.ingame.RenderableShaders;
import io.github.liticane.monoxide.util.render.shader.shaders.GradientShader;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;
import io.github.liticane.monoxide.util.render.RenderUtil;

import java.awt.*;
import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Atani Golden", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class GoldenModuleList extends ModuleListElement {

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

        FontRenderer roboto17 = FontStorage.getInstance().findFont("SF UI", 17);
        RenderableShaders.renderAndRun(() -> {
            float moduleY = leftY.get();
            RenderUtil.startScissorBox();
            RenderUtil.drawScissorBox(7, 0, scaledResolution.getScaledWidth() - 7, scaledResolution.getScaledHeight());
            for (Module module : moduleHashMap.keySet()) {
                float moduleHeight = roboto17.FONT_HEIGHT + 4;
                if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                    String name = module.getName();
                    float rectWidth = roboto17.getStringWidthInt(name) + 5;
                    float moduleX = 7 - (rectWidth + 7) + (float) (moduleHashMap.get(module).getOutput() * (rectWidth + 7));
                    RenderUtil.drawRect(moduleX, moduleY, rectWidth, moduleHeight, new Color(20, 20, 20).getRGB());
                    roboto17.drawTotalCenteredStringWithShadow(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 + 0.5f, -1);
                    moduleY += moduleHeight;
                }
            }
            RenderUtil.endScissorBox();
            GradientShader.drawGradientTB(5, leftY.get(), 2, moduleY - leftY.get(), 1, new Color(GOLDEN_FIRST), new Color(GOLDEN_SECOND));
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
