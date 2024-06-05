package gq.vapu.czfclient.Module.Modules.Render;

import com.mojang.realmsclient.gui.ChatFormatting;
import gq.vapu.czfclient.Manager.Manager;
import gq.vapu.czfclient.UI.Font.FontLoaders;
import gq.vapu.czfclient.Util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Timer;

import java.awt.*;

public class Sense implements Manager {

    @Override
    public void init() {
        Minecraft mc = Minecraft.getMinecraft();
        int rainbowTick = 0;
        Color rainbow2 = new Color(Color.HSBtoRGB(
                (float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6))
                        % 1.0f,
                0.5f, 1.0f));
        String text = " \247w" + "Czf" + ChatFormatting.LIGHT_PURPLE + "Sense" + "\247w" + " | " + Minecraft.getMinecraft().thePlayer.getName() + " | " + Timer.timerSpeed + " timerspeed";

//        RenderUtil.drawBordered(5, 5, 7 + FontLoaders.GoogleSans16.getStringWidth(text), 15, 1, new Color(0, 0, 0, 115).getRGB(), new Color(210, 132, 246, 255).getRGB());
        //BlurUtil.blurAreaBoarder(6, 60, 268, 42, 113);
        RenderUtil.drawBordered(7, 7, 7 + FontLoaders.GoogleSans16.getStringWidth(text) - 4, 11, 1, new Color(0, 0, 0, 48).getRGB(), new Color(210, 132, 246).getRGB());
//        Color sense1 = new Color(Color.HSBtoRGB(
//                (float) ((double) mc.thePlayer.ticksExisted / 50.0 + Math.sin((double) rainbowTick / 50.0 * 1.6))
//                        % 1.0f,
//                0.5f, 1.0f));
//        Color sense2 = new Color(Color.HSBtoRGB(
//                (float) ((double) mc.thePlayer.ticksExisted / 80.0 + Math.sin((double) rainbowTick / 80.0 * 1.6))
//                        % 1.0f,
//                0.5f, 1.0f));
//        RenderUtil.drawGradientSideways(7, 17, 8 + FontLoaders.GoogleSans16.getStringWidth(text), 18, sense1.getRGB(), sense2.getRGB());
        FontLoaders.GoogleSans16.drawString(text, 7, 9, -1);
    }
}
