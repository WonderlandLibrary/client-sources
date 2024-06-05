/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.GUI.beta.FocusBot;

import drunkclient.beta.UTILS.render.RenderUtil;
import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class FocusBot
extends GuiScreen {
    private boolean close = false;
    private boolean closed;

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        RenderUtil.blur(0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight());
        Gui.drawRect(sr.getScaledWidth() - 600, sr.getScaledHeight() - 390, sr.getScaledWidth() - 275, sr.getScaledHeight() - 125, new Color(36, 36, 36).getRGB());
        Gui.drawRect(sr.getScaledWidth() - 685, sr.getScaledHeight() - 390, sr.getScaledWidth() - 604, sr.getScaledHeight() - 125, new Color(36, 36, 36).getRGB());
        super.drawScreen(x, y, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!this.closed && keyCode == 1) {
            this.close = true;
            this.mc.mouseHelper.grabMouseCursor();
            this.mc.inGameHasSwider = true;
            return;
        }
        if (!this.close) return;
        this.mc.displayGuiScreen(null);
    }

    @Override
    public void initGui() {
        super.initGui();
    }
}

