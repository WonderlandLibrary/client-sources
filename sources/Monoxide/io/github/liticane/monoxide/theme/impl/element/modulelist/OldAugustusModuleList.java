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

import java.awt.*;
import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Augustus 2.6", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class OldAugustusModuleList extends ModuleListElement {

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
        for (Module module : moduleHashMap.keySet()) {
            if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                float moduleHeight = fontRenderer.FONT_HEIGHT + 2;
                float rectLength = (float) ((fontRenderer.getStringWidthInt(module.getName()) + 1) * moduleHashMap.get(module).getOutput());
                RenderUtil.drawRect(scaledResolution.getScaledWidth() - rectLength, moduleY, rectLength, moduleHeight, new Color(0, 0, 0, 100).getRGB());
                fontRenderer.drawStringWithShadow(module.getName(), scaledResolution.getScaledWidth() - rectLength + 0.5f, moduleY + moduleHeight / 2 - fontRenderer.FONT_HEIGHT / 2, new Color(0, 0, 255).getRGB());
                // The 20 is there so the rect goes out of the screen and therefore the right part's not rounded
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
