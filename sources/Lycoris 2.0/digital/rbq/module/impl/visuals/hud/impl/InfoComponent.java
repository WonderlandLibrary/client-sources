/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.module.impl.visuals.hud.impl;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import digital.rbq.module.impl.visuals.hud.Component;
import digital.rbq.module.impl.visuals.hud.HUDMod;
import digital.rbq.utils.render.Palette;

public final class InfoComponent
extends Component {
    public InfoComponent(HUDMod parent) {
        super(parent);
    }

    @Override
    public void draw(ScaledResolution sr) {
        HUDMod hud = this.getParent();
        int height = sr.getScaledHeight();
        FontRenderer fr = hud.defaultFont.getValue() != false ? InfoComponent.mc.fontRendererObj : InfoComponent.mc.fontRenderer;
        int color = Palette.fade((Color)hud.color.getValue()).getRGB();
        String fps = String.format("FPS\u00a77: %d", Minecraft.getDebugFPS());
        int fontHeight = 9;
        switch ((HUDMod.InfoDisplayMode)((Object)hud.infoDisplayMode.getValue())) {
            case LEFT: {
                if (InfoComponent.mc.currentScreen instanceof GuiChat) {
                    fontHeight += 15;
                }
                fr.drawStringWithShadow(fps, 2.0f, height - fontHeight, color);
                break;
            }
            case RIGHT: {
                int width = sr.getScaledWidth();
                fr.drawStringWithShadow(fps, (float)(width - fr.getStringWidth(fps)) - 2.0f, height - 9, color);
            }
        }
    }
}

