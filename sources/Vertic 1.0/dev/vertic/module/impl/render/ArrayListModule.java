package dev.vertic.module.impl.render;

import dev.vertic.Client;
import dev.vertic.event.api.EventLink;
import dev.vertic.event.impl.render.Render2DEvent;
import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.ModeSetting;
import dev.vertic.setting.impl.NumberSetting;
import dev.vertic.util.animation.Animation;
import dev.vertic.util.render.BlurUtil;
import dev.vertic.util.render.DrawUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class ArrayListModule extends Module {

    private final ModeSetting font = new ModeSetting("Font", "Product Sans", "Product Sans", "Jetbrains Mono", "Minecraft");
    private final ModeSetting sorting = new ModeSetting("Sorting", "Length", "Alphabetical", "Length");
    private final NumberSetting padding = new NumberSetting("Padding", 5, 0, 15, 1);
    private final NumberSetting backgroundOpacity = new NumberSetting("Background opacity", 125, 0, 255, 5);
    private final ModeSetting style = new ModeSetting("Style", "Vertic", "Vertic", "Whatever this is");

    private ArrayList<Module> modules = new ArrayList<>();

    private int x, y;
    private boolean doBlur = false;
    private int blurRadius;

    public ArrayListModule() {
        super("ArrayList", "Shows a list of modules enabled on screen.", Category.RENDER);
        this.addSettings(style, font, sorting, padding, backgroundOpacity);
        this.enable();
    }

    @EventLink
    public void onRender2D(Render2DEvent event) {
        int offset = 0;
        x = padding.getInt();
        y = padding.getInt();
        modules.clear();
        Client.instance.getModuleManager().modules.stream().filter(module -> module.isEnabled() && !module.getHidden().isEnabled()).forEach(module -> this.modules.add(module));
        doBlur = Client.instance.getModuleManager().getModule(Blur.class).isEnabled() && Client.instance.getModuleManager().getModule(Blur.class).arrayList.isEnabled();
        blurRadius = Client.instance.getModuleManager().getModule(Blur.class).radius.getInt();
        sort();
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        if (doBlur) {
            BlurUtil.doBlur(
                    blurRadius,
                    () -> {
                        for (Module m : modules) {
                            String name = m.getName();
                            if (m.getSuffix() != null) {
                                name += "§7" + m.getSuffix();
                            }
                            DrawUtil.rect(x - 2, y - 2, getStringWidth(name) + 4, getStringHeight(name) + 3, true, new Color(0, 0, 0, 150));
                            y += getStringHeight(name) + 3;
                        }
                    }
            );
        }
        y = padding.getInt();
        for (Module m : modules) {
            String name = m.getName();
            if (m.getSuffix() != null) {
                name += " §7" + m.getSuffix();
            }
            DrawUtil.rect(x - 2, y - 2, getStringWidth(name) + 4, getStringHeight(name) + 3, true, new Color(0, 0, 0, backgroundOpacity.getInt()));
            drawString(name, x, y, Client.instance.getModuleManager().getModule(ClientTheme.class).getColor(offset).getRGB());
            offset -= 120;
            y += getStringHeight(name) + 3;
        }
    }

    public void sort() {
        switch (sorting.getMode()) {
            case "Alphabetical":
                modules.sort(Comparator.comparing(Module::getName));
                break;
            case "Length":
                modules.sort(Comparator.comparing(m -> -getStringWidth(m.getSuffix() == null ? m.getName() : m.getName() + " §7" + m.getSuffix())));
        }
    }

    public void drawString(final String text, final float x, final float y, final int color) {
        switch (font.getMode()) {
            case "Product Sans":
                productSans20.drawStringWithShadow(text, x, y, color);
                break;
            case "Jetbrains Mono":
                cfont20.drawStringWithShadow(text, x, y, color);
                break;
            default:
                mc.fontRendererObj.drawStringWithShadow(text, x, y, color);
        }
    }

    public float getStringWidth(final String text) {
        switch (font.getMode()) {
            case "Product Sans":
                return productSans20.getStringWidth(text);
            case "Jetbrains Mono":
                return cfont20.getStringWidth(text);
            default:
                return mc.fontRendererObj.getStringWidth(text);
        }
    }

    public float getStringHeight(final String text) {
        switch (font.getMode()) {
            case "Product Sans":
                return productSans20.getStringHeight(text);
            case "Jetbrains Mono":
                return cfont20.getStringHeight(text);
            default:
                return mc.fontRendererObj.FONT_HEIGHT;
        }
    }

}
