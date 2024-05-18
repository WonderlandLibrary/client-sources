/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.ui.ingame;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.module.ModuleManager;
import pw.vertexcode.nemphis.ui.ingame.render.RenderComponent;
import pw.vertexcode.util.module.types.ToggleableModule;

public class Interface
extends RenderComponent {
    FontRenderer fr;

    public Interface() {
        this.fr = Minecraft.getMinecraft().fontRendererObj;
    }

    @Override
    public void render() {
        this.drawWatermark();
        this.drawArrayList();
        super.render();
    }

    public void drawWatermark() {
        this.fr.drawStringWithShadow("Nemphis \u00a77v" + Nemphis.instance.clientVersion, 2.0f, 2.0f, -1);
        EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        Minecraft.getMinecraft();
        this.fr.drawStringWithShadow("FPS \u00a77(" + Minecraft.debugFPS + ")", 2.0f, sr.getScaledHeight() - this.fr.FONT_HEIGHT * 2, -1);
        this.fr.drawStringWithShadow("XYZ \u00a77(" + (int)p.posX + " " + (int)p.posY + " " + (int)p.posZ + ")", 2.0f, sr.getScaledHeight() - this.fr.FONT_HEIGHT, -1);
    }

    public void drawArrayList() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int buffer = 2;
        Comparator<ToggleableModule> var12 = new Comparator<ToggleableModule>(){

            @Override
            public int compare(ToggleableModule o1, ToggleableModule o2) {
                if (!o1.renderMode.equalsIgnoreCase(".") || !o2.renderMode.equalsIgnoreCase(".")) {
                    return Interface.this.fr.getStringWidth(String.valueOf(o1.getName()) + " " + o1.renderMode) > Interface.this.fr.getStringWidth(String.valueOf(o2.getName()) + " " + o2.renderMode) ? -1 : (Interface.this.fr.getStringWidth(String.valueOf(o2.getName()) + " " + o2.renderMode) > Interface.this.fr.getStringWidth(String.valueOf(o1.getName()) + " " + o1.renderMode) ? 1 : 0);
                }
                return Interface.this.fr.getStringWidth(o1.getName()) > Interface.this.fr.getStringWidth(o2.getName()) ? -1 : (Interface.this.fr.getStringWidth(o2.getName()) > Interface.this.fr.getStringWidth(o1.getName()) ? 1 : 0);
            }
        };
        Collections.sort(Nemphis.instance.modulemanager.getMods(), var12);
        for (ToggleableModule mod : Nemphis.instance.modulemanager.getMods()) {
            if (!mod.isEnabled()) continue;
            int width = sr.getScaledWidth() - (this.fr.getStringWidth(mod.getName()) + 1);
            if (!mod.renderMode.equalsIgnoreCase(".")) {
                width = sr.getScaledWidth() - (this.fr.getStringWidth(String.valueOf(mod.getName()) + " " + mod.renderMode) + 1);
                this.fr.drawStringWithShadow(String.valueOf(mod.getName()) + " \u00a77" + mod.renderMode, width, buffer, mod.getColor());
            } else {
                this.fr.drawStringWithShadow(mod.getName(), width, buffer, mod.getColor());
            }
            buffer += this.fr.FONT_HEIGHT + 1;
        }
    }

}

