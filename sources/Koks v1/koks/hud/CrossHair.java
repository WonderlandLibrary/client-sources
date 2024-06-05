package koks.hud;

import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 16:27
 */
public class CrossHair {

    public void drawCrosshair() {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        RenderUtils renderUtils = new RenderUtils();

        float length = 3;
        float gap = 1;

        if (!GuiIngame.showCrosshair)
            return;

        // Top
        renderUtils.drawRect(7, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 - gap - length, sr.getScaledWidth() / 2 + 1, sr.getScaledHeight() / 2 - gap, Color.WHITE);

        // Bottom
        renderUtils.drawRect(7, sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 + gap + length + 1, sr.getScaledWidth() / 2 + 1, sr.getScaledHeight() / 2 + gap + 1, Color.WHITE);

        // Left
        renderUtils.drawRect(7, sr.getScaledWidth() / 2 - gap - length, sr.getScaledHeight() / 2, sr.getScaledWidth() / 2 - gap, sr.getScaledHeight() / 2 + 1, Color.WHITE);

        // Right
        renderUtils.drawRect(7, sr.getScaledWidth() / 2 + gap + length + 1, sr.getScaledHeight() / 2, sr.getScaledWidth() / 2 + gap + 1, sr.getScaledHeight() / 2 + 1, Color.WHITE);
    }

}