/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl;

import java.awt.Color;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import digital.rbq.module.impl.visuals.hud.Component;
import digital.rbq.module.impl.visuals.hud.HUDMod;
import digital.rbq.utils.render.Palette;

public final class Watermark
extends Component {
    public Watermark(HUDMod parent) {
        super(parent);
    }

    @Override
    public void draw(ScaledResolution sr) {
        FontRenderer fr = this.getParent().defaultFont.getValue() != false ? Watermark.mc.fontRendererObj : Watermark.mc.fontRenderer;
        String firstLetter = HUDMod.clientName.substring(0, 1);
        fr.drawStringWithShadow(firstLetter, 2.0f, 2.0f, Palette.fade((Color)this.getParent().color.getValue()).getRGB());
        fr.drawStringWithShadow(HUDMod.clientName.substring(1), fr.getStringWidth(firstLetter) + 2, 2.0f, -1);
    }
}

