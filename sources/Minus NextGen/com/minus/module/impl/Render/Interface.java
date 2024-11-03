package com.minus.module.impl.Render;

import com.minus.Client;
import com.minus.event.EventRender2D;
import com.minus.event.events.annotations.EventLink;
import com.minus.event.events.bus.Listener;
import com.minus.module.Category;
import com.minus.module.setting.BooleanSetting;
import com.minus.module.setting.ModeSetting;
import com.minus.module.setting.NumberSetting;
import com.minus.module.Module;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Interface extends Module {
    public static final BooleanSetting watermark = new BooleanSetting("Watermark", true);
    public static final ModeSetting watermarkMode = new ModeSetting("Watermark Mode", "Simple", "Simple");
    public static final BooleanSetting arrayList = new BooleanSetting("ArrayList", true);
    public static final ModeSetting colorMode = new ModeSetting("Color Mode", "Astolfo", "Astolfo");
    public static final NumberSetting padding = new NumberSetting("Offset", 0, 40, 5, 1);

    public Interface() {
        super("Interface", "Clients HUD", GLFW.GLFW_KEY_Y, Category.RENDER);
        addSettings(watermark, watermarkMode, arrayList, colorMode, padding);
    }

    @EventLink
    public final Listener<EventRender2D> eventRender2DListener = event -> {
        int width = event.getWidth();
        if(mc.currentScreen != null){
            return;
        }
        if (watermark.getValue()) {
            switch (watermarkMode.getMode()) {
                case "Simple":
                    event.getContext().drawText(mc.textRenderer, "Minus", 3, 3, -1, true);
                    break;
            }
        }

        if (arrayList.getValue()) {
            List<Module> enabledModules = Client.INSTANCE.getModuleManager().getEnabledModules().stream().sorted(Comparator.comparingDouble(ri -> -mc.textRenderer.getWidth(getFullName(ri)))).collect(Collectors.toList());
            int i = padding.getValueInt();

            for (Module m : enabledModules) {
                if (m.getCategory() == Category.RENDER) {
                    continue;
                }

                int color = switch (colorMode.getMode()) {
                    case "Astolfo" -> getAstolfo(i * 3);
                    default -> 0;
                };

                event.getContext().fill(width - mc.textRenderer.getWidth(getFullName(m)) - padding.getValueInt() - 2, i, width - padding.getValueInt() + 2, i + 1 + mc.textRenderer.fontHeight, color);
                event.getContext().drawText(mc.textRenderer, getFullName(m), width - mc.textRenderer.getWidth(getFullName(m)) - padding.getValueInt(), i + 1, color, true);
                i += 10;
            }
        }
    };

    private String getIP() {
        if (mc.world == null) {
            return "NULL";
        } else {
            if (mc.isInSingleplayer()) {
                return "Singleplayer";
            } else {
                return mc.getCurrentServerEntry().address;
            }
        }
    }

    private String getFullName(Module m) {
        if (m.getSuffix() == null) {
            return m.getName();
        } else {
            return m.getName() + " " + "§8" + "[" + "§7" + m.getSuffix() + "§8" + "]";
        }
    }

    public static int getAstolfo(int offset) {
        int i = (int) ((System.currentTimeMillis() / 11 + offset) % 360);
        i = (i > 180 ? 360 - i : i) + 180;
        return Color.HSBtoRGB(i / 360f, 0.55f, 1f);
    }
}