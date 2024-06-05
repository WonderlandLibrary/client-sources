/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.API.GUI.betaui;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class BetaClickUI
extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution sr = new ScaledResolution(this.mc);
        Gui.drawRect(0.0f, 0.0f, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 20, 51, 180).getRGB());
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

