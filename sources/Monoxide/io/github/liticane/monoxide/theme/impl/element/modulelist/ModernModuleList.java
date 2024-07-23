package io.github.liticane.monoxide.theme.impl.element.modulelist;

import io.github.liticane.monoxide.util.render.font.FontStorage;
import io.github.liticane.monoxide.module.ModuleManager;
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
import io.github.liticane.monoxide.module.impl.hud.ModuleListModule;
import io.github.liticane.monoxide.value.Value;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.color.ColorUtil;

import java.awt.*;
import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Atani Modern", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class ModernModuleList extends ModuleListElement {

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
        FontRenderer fontRenderer = FontStorage.getInstance().findFont("Greycliff Medium", 18);
        RenderableShaders.render(true, false, () -> {
            float moduleY = rightY.get();
            int counter = 0;
            for (Module module : moduleHashMap.keySet()) {
                float moduleHeight = fontRenderer.FONT_HEIGHT + 2;
                if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                    String name = module.getName();
                    Color firstColor = new Color(red.getValue(), green.getValue(), blue.getValue());
                    int color = ColorUtil.fadeBetween(firstColor.getRGB(), firstColor.brighter().getRGB(), counter * 150L);
                    float rectWidth = (fontRenderer.getStringWidthInt(name) + 3);
                    float rectLength = (float) (rectWidth * moduleHashMap.get(module).getOutput());
                    float moduleX = scaledResolution.getScaledWidth() - rectLength + 0.5f;
                    RenderUtil.drawRect(moduleX, moduleY, rectWidth, moduleHeight, color);
                    moduleY += moduleHeight;
                    counter++;
                }
            }
        });
        float moduleY = rightY.get();
        for (Module module : moduleHashMap.keySet()) {
            float moduleHeight = fontRenderer.FONT_HEIGHT + 2;
            if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                String name = module.getName();
                float rectWidth = (fontRenderer.getStringWidthInt(name) + 3);
                float rectLength = (float) (rectWidth * moduleHashMap.get(module).getOutput());
                float moduleX = scaledResolution.getScaledWidth() - rectLength + 0.5f;
                RenderUtil.drawRect(moduleX, moduleY, rectWidth, moduleHeight, new Color(20, 20, 20).getRGB());
                fontRenderer.drawTotalCenteredString(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 - 0.5f, -1);
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
        return FontStorage.getInstance().findFont("Greycliff Medium", 18);
    }

}
