package io.github.liticane.monoxide.theme.impl.element.modulelist;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.theme.data.ThemeObjectInfo;
import io.github.liticane.monoxide.theme.data.enums.ElementType;
import io.github.liticane.monoxide.theme.data.enums.ThemeObjectType;
import io.github.liticane.monoxide.theme.impl.element.ModuleListElement;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import io.github.liticane.monoxide.util.render.shader.shaders.GradientShader;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;
import io.github.liticane.monoxide.util.render.shader.shaders.RoundedShader;

import java.awt.*;
import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Icarus", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class IcarusModuleList extends ModuleListElement {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleHashMap) {
        FontRenderer fontRenderer = FontStorage.getInstance().findFont("Pangram Regular", 17);
        float moduleY = rightY.get();
        float gradientWidth = 1f;
        for (Module module : moduleHashMap.keySet()) {
            if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                float moduleHeight = fontRenderer.FONT_HEIGHT + 4;
                float rectLength = (float) ((fontRenderer.getStringWidthInt(module.getName() + 3) * moduleHashMap.get(module).getOutput()) - gradientWidth);
                RoundedShader.drawRound(scaledResolution.getScaledWidth() - rectLength, moduleY, rectLength + 20, moduleHeight, 2, new Color(21, 21, 21));
                fontRenderer.drawString(module.getName(), scaledResolution.getScaledWidth() - rectLength + 1.5f, moduleY + moduleHeight / 2 - fontRenderer.FONT_HEIGHT / 2, -1);
                // The 20 is there so the rect goes out of the screen and therefore the right part's not rounded
                moduleY += moduleHeight;
            }
        }
        GradientShader.drawGradientTB(scaledResolution.getScaledWidth() - gradientWidth, rightY.get(), gradientWidth, moduleY + 0.5f, 1, new Color(ICARUS_FIRST), new Color(ICARUS_SECOND));
    }

    @Override
    public boolean shouldAnimate() {
        return true;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return FontStorage.getInstance().findFont("Pangram Regular", 17);
    }

}
