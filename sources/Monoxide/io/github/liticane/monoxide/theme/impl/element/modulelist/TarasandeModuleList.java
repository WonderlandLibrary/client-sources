package io.github.liticane.monoxide.theme.impl.element.modulelist;

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

import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Tarasande", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class TarasandeModuleList extends ModuleListElement {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleHashMap) {
        int color = TARASANDE;
        FontRenderer fontRenderer = mc.fontRendererObj;
        float moduleY = rightY.get();
        float y = moduleY, panelWidth = 100, titleBarHeight = fontRenderer.FONT_HEIGHT, x = scaledResolution.getScaledWidth() - panelWidth;
        RenderUtil.drawRect(x, y, panelWidth, titleBarHeight, color);
        fontRenderer.drawStringWithShadow("Array List", x + 0.5f, y + 0.5f, -1);
        moduleY += titleBarHeight - 1;
        for (Module module : moduleHashMap.keySet()) {
            float moduleHeight = fontRenderer.FONT_HEIGHT + 1;
            if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                String name = module.getName();
                float rectWidth = fontRenderer.getStringWidthInt(name) + 5;
                float moduleX = (float) (scaledResolution.getScaledWidth() + 1 - (rectWidth * moduleHashMap.get(module).getOutput()));
                fontRenderer.drawTotalCenteredStringWithShadow(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 + 0.5f, color);
                moduleY += moduleHeight;
            }
        }
    }

    @Override
    public boolean shouldAnimate() {
        return false;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return mc.fontRendererObj;
    }

}
