package io.github.liticane.monoxide.theme.impl.element.modulelist;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.module.ModuleManager;
import io.github.liticane.monoxide.theme.data.ThemeObjectInfo;
import io.github.liticane.monoxide.theme.data.enums.ElementType;
import io.github.liticane.monoxide.theme.data.enums.ThemeObjectType;
import io.github.liticane.monoxide.theme.impl.element.ModuleListElement;
import io.github.liticane.monoxide.util.render.animation.advanced.Direction;
import io.github.liticane.monoxide.util.render.animation.advanced.impl.DecelerateAnimation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import io.github.liticane.monoxide.module.api.Module;
import io.github.liticane.monoxide.module.impl.hud.ModuleListModule;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;
import io.github.liticane.monoxide.util.render.color.ColorUtil;

import java.awt.*;
import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "OHareWare", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class OHareWareModuleList extends ModuleListElement {

    private NumberValue<Integer> red = new NumberValue<>("Red", this, 255, 0, 255, 0);
    private NumberValue<Integer> green = new NumberValue<>("Green", this, 255, 0, 255, 0);
    private NumberValue<Integer> blue = new NumberValue<>("Blue", this, 255, 0, 255, 0);

    @Override
    public void onEnable() {
        ModuleListModule moduleList = ModuleManager.getInstance().getModule(ModuleListModule.class);
        for(Value value : ValueManager.getInstance().getValues(this, false))
            ValueManager.getInstance().addLinkedValues(moduleList, value);
    }

    @Override
    public void onDisable() {
        ModuleListModule moduleList = ModuleManager.getInstance().getModule(ModuleListModule.class);
        for(Value value : ValueManager.getInstance().getValues(this, false))
            ValueManager.getInstance().removeLinkedValues(moduleList, value);
    }

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleHashMap) {
        FontRenderer fontRenderer = FontStorage.getInstance().findFont("Tahoma", 19);
        float moduleY = rightY.get();
        int counter = 0;
        for (Module module : moduleHashMap.keySet()) {
            if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                float moduleHeight = fontRenderer.FONT_HEIGHT + 1f;
                float rectLength = (float) ((fontRenderer.getStringWidthInt(module.getName()) * moduleHashMap.get(module).getOutput()));
                Color firstColor = new Color(red.getValue(), green.getValue(), blue.getValue());
                fontRenderer.drawStringWithShadow(module.getName(), scaledResolution.getScaledWidth() - rectLength - 1, moduleY + moduleHeight / 2 - fontRenderer.FONT_HEIGHT / 2, ColorUtil.fadeBetween(firstColor.getRGB(), ColorUtil.darken(firstColor.getRGB(), 0.49F), counter * 50L));
                moduleY += moduleHeight;
                counter++;
            }
        }
    }

    @Override
    public boolean shouldAnimate() {
        return false;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return FontStorage.getInstance().findFont("Tahoma", 19);
    }

}
