/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render.hudkirkab11;

import java.text.SimpleDateFormat;
import java.util.Date;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Theme;
import me.thekirkayt.event.events.Render2DEvent;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.renderer.GlStateManager;

public class Gradient Theme #1
extends Theme {
    private static final int MIN_HEX = -11141121;
    private static final int MAX_HEX = -11184641;
    private static final int MAX_FADE = -11141121;
    private static int currentColor;
    private static int fadeState;
    private static boolean goingUp;

    public Gradient Theme #1(String name, boolean value, Module module) {
        super(name, value, module);
    }

    @Override
    public boolean onRender(Render2DEvent event) {
        if (super.onRender(event)) {
            String time = new SimpleDateFormat("hh:mm a").format(new Date());
            if (time.startsWith("0")) {
                time = time.replaceFirst("0", "");
            }
            GlStateManager.pushMatrix();
            ClientUtils.clientFont().drawStringWithShadow("\u00a79\u00a7lK\u00a77\u00a7lirka \u00a79\u00a7lb\u00a77\u00a7l11", 2.0, 2.0, -11141121);
            GlStateManager.popMatrix();
            int y = 2;
            for (Module mod : ModuleManager.getModulesForRender()) {
                if (!mod.drawDisplayName(event.getWidth() - ClientUtils.clientFont().getStringWidth(String.format("%s" + (mod.getSuffix().length() > 0 ? " \u00a70[%s]" : ""), mod.getDisplayName(), mod.getSuffix())) - 2, y, currentColor)) continue;
                y += 10;
            }
        }
        return super.onRender(event);
    }

    public static void updateFade() {
        if (fadeState >= 20 || fadeState <= 0) {
            boolean bl = goingUp = !goingUp;
        }
        fadeState = goingUp ? ++fadeState : --fadeState;
        double ratio = (double)fadeState / 20.0;
        currentColor = Gradient Theme #1.getFadeHex(-23614, -11184641, ratio);
    }

    private static int getFadeHex(int hex1, int hex2, double ratio) {
        int r = hex1 >> 16;
        int g = hex1 >> 8 & 255;
        int b = hex1 & 255;
        r += (int)((double)((hex2 >> 16) - r) * ratio);
        g += (int)((double)((hex2 >> 8 & 255) - g) * ratio);
        b += (int)((double)((hex2 & 255) - b) * ratio);
        return r << 16 | g << 8 | b;
    }
}

