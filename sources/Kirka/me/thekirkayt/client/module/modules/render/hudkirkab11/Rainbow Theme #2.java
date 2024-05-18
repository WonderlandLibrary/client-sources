/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.render.hudkirkab11;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.module.modules.render.hudkirkab11.Theme;
import me.thekirkayt.event.events.Render2DEvent;
import me.thekirkayt.utils.ClientUtils;

public class Rainbow Theme #2
extends Theme {
    private float[] hue = new float[]{0.0f};

    public Rainbow Theme #2(String string, boolean bl, Module module) {
        super(string, bl, module);
    }

    @Override
    public boolean onRender(Render2DEvent render2DEvent) {
        if (super.onRender(render2DEvent)) {
            float[] arrf;
            String string = new SimpleDateFormat("hh:mm a").format(new Date());
            if (string.startsWith("0")) {
                string = string.replaceFirst("0", "");
            }
            ClientUtils.clientFont().drawStringWithShadow("\u00a7lKirka b11", 2.0, 2.0, Color.getHSBColor(this.hue[0] / 255.0f, 1.0f, 1.0f).getRGB());
            int n = 2;
            float[] arrf2 = new float[]{this.hue[0]};
            for (Module object2 : ModuleManager.getModulesForRender()) {
                float[] arrf3;
                int arrf6 = Color.getHSBColor(arrf2[0] / 255.0f, 1.0f, 1.0f).getRGB();
                if (!object2.drawDisplayName(render2DEvent.getWidth() - ClientUtils.clientFont().getStringWidth(String.format("%s" + (object2.getSuffix().length() > 0 ? " \u00a70[%s]" : ""), object2.getDisplayName(), object2.getSuffix())) - 2, n, arrf6)) continue;
                float[] arrf4 = arrf3 = arrf2;
                arrf4[0] = arrf4[0] + 9.0f;
                if (arrf2[0] > 255.0f) {
                    float[] arrf5;
                    float[] arrf7 = arrf5 = arrf2;
                    arrf7[0] = arrf7[0] - 255.0f;
                }
                n += 10;
            }
            float[] arrf8 = arrf = this.hue;
            arrf8[0] = (float)((double)arrf8[0] + 0.5);
            if (this.hue[0] > 255.0f) {
                float[] arrf9;
                float[] arrf10 = arrf9 = this.hue;
                arrf10[0] = arrf10[0] - 255.0f;
            }
        }
        return super.onRender(render2DEvent);
    }
}

