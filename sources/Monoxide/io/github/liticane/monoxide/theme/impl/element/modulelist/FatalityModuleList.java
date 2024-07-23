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
import io.github.liticane.monoxide.util.render.color.ColorUtil;

import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Fatality", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class FatalityModuleList extends ModuleListElement {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleHashMap) {
        if (rightY.get() == 0)
            rightY.set(1);
        FontRenderer fontRenderer = mc.fontRendererObj;
        float moduleY = rightY.get();
        int counter = 0;
        for (Module module : moduleHashMap.keySet()) {
            if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                float moduleHeight = fontRenderer.FONT_HEIGHT;
                float rectLength = (float) ((fontRenderer.getStringWidthInt(module.getName()) + 1) * moduleHashMap.get(module).getOutput());
                fontRenderer.drawStringWithShadow(module.getName(), scaledResolution.getScaledWidth() - rectLength - 1, moduleY + moduleHeight / 2 - fontRenderer.FONT_HEIGHT / 2, ColorUtil.fadeBetween(FATALITY_FIRST, FATALITY_SECOND, counter * 150L));
                moduleY += moduleHeight;
                counter++;
            }
        }
    }

    @Override
    public boolean shouldAnimate() {
        return true;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return mc.fontRendererObj;
    }

}
