package io.github.liticane.monoxide.theme.impl.element.modulelist;

import java.util.function.Supplier;
import com.mojang.realmsclient.gui.ChatFormatting;
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
import io.github.liticane.monoxide.value.impl.BooleanValue;
import io.github.liticane.monoxide.value.impl.NumberValue;
import io.github.liticane.monoxide.value.impl.ModeValue;
import io.github.liticane.monoxide.value.ValueManager;
import io.github.liticane.monoxide.util.math.atomic.AtomicFloat;
import io.github.liticane.monoxide.util.render.RenderUtil;
import io.github.liticane.monoxide.util.render.color.ColorUtil;

import java.awt.*;
import java.util.Calendar;
import java.util.LinkedHashMap;

@ThemeObjectInfo(name = "Custom", themeObjectType = ThemeObjectType.ELEMENT, elementType = ElementType.MODULE_LIST)
public class CustomModuleList extends ModuleListElement {

    private ModeValue arrayListPosition = new ModeValue("Module List Position", this, new String[]{"Left", "Right"});
    private ModeValue customColorMode = new ModeValue("Custom Color Mode", this, new String[]{"Static", "Random", "Fade", "Gradient", "Rainbow", "Astolfo Sky"});
    private NumberValue<Integer> red = new NumberValue<>("Red", this, 255, 0, 255, 0, new Supplier[]{() -> (customColorMode.getValue().equalsIgnoreCase("Static") || customColorMode.getValue().equalsIgnoreCase("Random") || customColorMode.getValue().equalsIgnoreCase("Fade") || customColorMode.getValue().equalsIgnoreCase("Gradient"))});
    private NumberValue<Integer> green = new NumberValue<>("Green", this, 255, 0, 255, 0, new Supplier[]{() -> (customColorMode.getValue().equalsIgnoreCase("Static") || customColorMode.getValue().equalsIgnoreCase("Random") || customColorMode.getValue().equalsIgnoreCase("Fade") || customColorMode.getValue().equalsIgnoreCase("Gradient"))});
    private NumberValue<Integer> blue = new NumberValue<>("Blue", this, 255, 0, 255, 0, new Supplier[]{() -> (customColorMode.getValue().equalsIgnoreCase("Static") || customColorMode.getValue().equalsIgnoreCase("Random") || customColorMode.getValue().equalsIgnoreCase("Fade") || customColorMode.getValue().equalsIgnoreCase("Gradient"))});
    private NumberValue<Integer> red2 = new NumberValue<>("Second Red", this, 255, 0, 255, 0, new Supplier[]{() -> customColorMode.getValue().equalsIgnoreCase("Gradient")});
    private NumberValue<Integer> green2 = new NumberValue<>("Second Green", this, 255, 0, 255, 0, new Supplier[]{() -> customColorMode.getValue().equalsIgnoreCase("Gradient")});
    private NumberValue<Integer> blue2 = new NumberValue<>("Second Blue", this, 255, 0, 255, 0, new Supplier[]{() -> customColorMode.getValue().equalsIgnoreCase("Gradient")});
    private NumberValue<Float> darkFactor = new NumberValue<>("Dark Factor", this, 0.49F, 0F, 1F, 2, new Supplier[]{() -> customColorMode.getValue().equalsIgnoreCase("Fade")});
    private NumberValue<Float> rectWidth = new NumberValue<>("Rect Width", this, 2f, 0f, 10f, 1);
    private NumberValue<Float> rectHeight = new NumberValue<>("Rect Height", this, 1f, 0f, 10f, 1);
    private BooleanValue renderShaders = new BooleanValue("Render Shaders", this, true);
    private BooleanValue renderBlur = new BooleanValue("Render Blur", this, true, new Supplier[]{() -> renderShaders.getValue()});
    private BooleanValue renderBloom = new BooleanValue("Render Bloom", this, true, new Supplier[]{() -> renderShaders.getValue()});
    private BooleanValue suffix = new BooleanValue("Suffix", this, true);
    private ModeValue suffixMode = new ModeValue("Suffix Mode", this, new String[] {"nm sfx", "nm - sfx", "nm # sfx", "nm (sfx)", "nm [sfx]", "nm {sfx}", "nm - (sfx)", "nm - [sfx]", "nm - {sfx}", "nm # (sfx)", "nm # [sfx]", "nm # {sfx}"}, new Supplier[]{() -> suffix.getValue()});
    private ModeValue suffixColor = new ModeValue("Suffix Color", this, new String[] {"Gray", "Dark Gray", "White", "None"});
    private ModeValue fontMode = new ModeValue("Font", this, new String[]{"Minecraft", "SF UI", "SF UI Medium", "Product Sans", "Arial", "SF Regular", "SF Semibold", "Volte Semibold"});
    private NumberValue<Integer> fontSize = new NumberValue<>("Font Size", this, 19, 17, 21, 0, new Supplier[]{() -> !fontMode.getValue().equalsIgnoreCase("Minecraft")});
    private NumberValue<Integer> brightness = new NumberValue<>("Background Brightness", this, 0, 0, 255, 0);
    private NumberValue<Integer> opacity = new NumberValue<>("Background Opacity", this, 180, 0, 255, 0);
    private NumberValue<Float> xOffset = new NumberValue<>("X Offset", this, 0F, 0F, 20F, 1);
    private NumberValue<Float> yOffset = new NumberValue<>("Y Offset", this, 0F, 0F, 20F, 1);
    private BooleanValue fontShadow = new BooleanValue("Font Shadow", this, true);

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

    private LinkedHashMap<Module, Color> moduleColorHashMap = new LinkedHashMap<>();
    final Calendar calendar = Calendar.getInstance();

    @Override
    public void onDraw(ScaledResolution scaledResolution, float partialTicks, AtomicFloat leftY, AtomicFloat rightY, LinkedHashMap<Module, DecelerateAnimation> moduleHashMap) {
        switch (this.arrayListPosition.getValue()) {
            case "Left":
                leftY.set(leftY.get() + yOffset.getValue());
                break;
            case "Right":
                rightY.set(rightY.get() + yOffset.getValue());
                break;
        }
        RenderableShaders.renderAndRun(renderShaders.getValue() && renderBloom.getValue(), renderShaders.getValue() && renderBlur.getValue(), () -> {
            FontRenderer fontRenderer;
            switch (fontMode.getValue()) {
                case "SF UI":
                    fontRenderer = FontStorage.getInstance().findFont("SF UI", this.fontSize.getValue());
                    break;
                case "SF UI Medium":
                    fontRenderer = FontStorage.getInstance().findFont("SF UI Medium", this.fontSize.getValue());
                    break;
                case "Product Sans":
                    fontRenderer = FontStorage.getInstance().findFont("Product Sans", this.fontSize.getValue());
                    break;
                case "Arial":
                    fontRenderer = FontStorage.getInstance().findFont("Arial", this.fontSize.getValue());
                    break;
                case "SF Regular":
                    fontRenderer = FontStorage.getInstance().findFont("SF Regular", this.fontSize.getValue());
                    break;
                case "SF Semibold":
                    fontRenderer = FontStorage.getInstance().findFont("SF Semibold", this.fontSize.getValue());
                    break;
                case "Volte Semibold":
                    fontRenderer = FontStorage.getInstance().findFont("Volte Semibold", this.fontSize.getValue());
                    break;
                default:
                    fontRenderer = mc.fontRendererObj;
                    break;
            }
            float moduleY = arrayListPosition.is("Left") ? leftY.get() : rightY.get();
            int counter = 0;
            for (Module module : moduleHashMap.keySet()) {
                float moduleHeight = fontRenderer.FONT_HEIGHT + rectHeight.getValue();
                if (!moduleHashMap.get(module).finished(Direction.BACKWARDS)) {
                    int color = 0;
                    switch (this.customColorMode.getValue()) {
                        case "Static":
                            color = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
                            break;
                        case "Random":
                            if(!moduleColorHashMap.containsKey(module)) {
                                int baseHue = 15;
                                int minValue = 150;
                                int maxValue = 255;
                                int alpha = 255;
                                moduleColorHashMap.put(module, ColorUtil.generateRandomTonedColor(baseHue, minValue, maxValue, alpha));
                            }
                            color = moduleColorHashMap.get(module).getRGB();
                            break;
                        case "Fade": {
                            int firstColor = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
                            color = ColorUtil.fadeBetween(firstColor, ColorUtil.darken(firstColor, darkFactor.getValue()), counter * 150L);
                            break;
                        }
                        case "Gradient": {
                            int firstColor = new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB();
                            int secondColor = new Color(red2.getValue(), green2.getValue(), blue2.getValue()).getRGB();
                            color = ColorUtil.fadeBetween(firstColor, secondColor, counter * 150L);
                            break;
                        }
                        case "Rainbow":
                            color = ColorUtil.getRainbow(3000, (int) (counter * 150L));
                            break;
                        case "Astolfo Sky":
                            color = ColorUtil.blendRainbowColours(counter * 150L);
                            break;
                    }
                    String name = getModuleName(module, true);
                    float rectWidth = (fontRenderer.getStringWidthInt(name) + this.rectWidth.getValue());
                    float moduleX = this.arrayListPosition.getValue().equalsIgnoreCase("Left") ? (0 - rectWidth + (float) (moduleHashMap.get(module).getOutput() * rectWidth) + xOffset.getValue()) : scaledResolution.getScaledWidth() - ((float) (moduleHashMap.get(module).getOutput() * rectWidth) + xOffset.getValue());
                    RenderUtil.drawRect(moduleX, moduleY, rectWidth, moduleHeight, new Color(brightness.getValue(), brightness.getValue(), brightness.getValue(), opacity.getValue()).getRGB());

                    if (!fontShadow.getValue()) {
                        fontRenderer.drawTotalCenteredString(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 + 0.5f, color);
                    } else {
                        fontRenderer.drawTotalCenteredStringWithShadow(name, moduleX + rectWidth / 2, moduleY + moduleHeight / 2 + 0.5f, color);
                    }

                    moduleY += moduleHeight;
                    counter++;
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
        FontRenderer fontRenderer = null;
        switch (fontMode.getValue()) {
            case "SF UI":
                fontRenderer = FontStorage.getInstance().findFont("SF UI", this.fontSize.getValue());
                break;
            case "SF UI Medium":
                fontRenderer = FontStorage.getInstance().findFont("SF UI Medium", this.fontSize.getValue());
                break;
            case "Product Sans":
                fontRenderer = FontStorage.getInstance().findFont("Product Sans", this.fontSize.getValue());
                break;
            case "Arial":
                fontRenderer = FontStorage.getInstance().findFont("Arial", this.fontSize.getValue());
                break;
            case "SF Regular":
                fontRenderer = FontStorage.getInstance().findFont("SF Regular", this.fontSize.getValue());
                break;
            case "SF Semibold":
                fontRenderer = FontStorage.getInstance().findFont("SF Semibold", this.fontSize.getValue());
                break;
            case "Volte Semibold":
                fontRenderer = FontStorage.getInstance().findFont("Volte Semibold", this.fontSize.getValue());
                break;
            default:
                fontRenderer = mc.fontRendererObj;
                break;
        }
        return fontRenderer;
    }

    public String getModuleName(Module module, boolean colors) {
        String name = module.getName();
        if(module.getSuffix() != null && suffix.getValue()) {
            ChatFormatting chatFormatting = null;
            if(colors) {
                switch(this.suffixColor.getValue()) {
                    case "White":
                        chatFormatting = ChatFormatting.WHITE;
                        break;
                    case "Gray":
                        chatFormatting = ChatFormatting.GRAY;
                        break;
                    case "Dark Gray":
                        chatFormatting = ChatFormatting.DARK_GRAY;
                        break;
                    case "None":
                        chatFormatting = ChatFormatting.RESET;
                        break;
                }
            }
            name = suffixMode.getValue().replace("nm", module.getName() + (chatFormatting == null ? "" : chatFormatting.toString())).replace("sfx",  module.getSuffix());
        }
        return name;
    }

}
