package io.github.liticane.monoxide.theme.impl.element;

import io.github.liticane.monoxide.theme.ThemeObject;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;

import java.util.LinkedHashMap;

public abstract class ModuleListElement extends ThemeObject {

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, Object[] params) {
        onDraw(scaledResolution, partialTicks, leftY, rightY, (LinkedHashMap<Module, DecelerateAnimation>) params[0]);
    }

    public abstract void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleMap);

    public abstract boolean shouldAnimate();

    public abstract FontRenderer getFontRenderer();

}
