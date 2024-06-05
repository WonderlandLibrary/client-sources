package xyz.vergoclient.ui.hud.elements.watermarks;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRenderGUI;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.security.account.AccountUtils;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.Gl.BloomUtil;
import xyz.vergoclient.util.Gl.BlurUtil;
import xyz.vergoclient.util.main.*;

import java.awt.*;

public class simpleWatermark implements OnEventInterface {

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventRenderGUI && e.isPre()) {
            drawSimpleWatermark();
        }
    }

    private static void drawSimpleWatermark() {
        GlStateManager.pushMatrix();

        final int startColour = ColorUtils.fadeBetween(new Color(210, 8, 62).getRGB(), new Color(108, 51, 217).getRGB(), 0);
        final int endColour = ColorUtils.fadeBetween(new Color(108, 51, 217).getRGB(), new Color(210, 8, 62).getRGB(), 250);

        JelloFontRenderer fr1 = FontUtil.comfortaaSmall;

        String clientName = "Vergo - ";

        String serverName = ServerUtils.getServerIP() + " - ";

        String userName = AccountUtils.account.username;

        BloomUtil.drawAndBloom(() -> ColorUtils.glDrawSidewaysGradientRect(3, 5, (float) (fr1.getStringWidth(clientName) + fr1.getStringWidth(serverName) + fr1.getStringWidth(userName)) + 6, 1.5f, startColour, endColour));
        BlurUtil.blurArea(3, 6, (float) (fr1.getStringWidth(clientName) + fr1.getStringWidth(serverName) + fr1.getStringWidth(userName)) + 6, 12f);
        RenderUtils.drawAlphaRoundedRect(3, 6, (float) (fr1.getStringWidth(clientName) + fr1.getStringWidth(serverName) + fr1.getStringWidth(userName)) + 6, 12f, 0f, new Color(60, 60, 60, 100));

        fr1.drawString(clientName, 5, 11, 0xffffffff);
        fr1.drawString(serverName, fr1.getStringWidth(clientName) + 5, 11, 0xffffffff);
        fr1.drawString(userName, fr1.getStringWidth(clientName) + fr1.getStringWidth(serverName) + 7, 11, 0xffffffff);

        GlStateManager.popMatrix();
    }
}
