package xyz.vergoclient.ui.hud.elements.watermarks;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventRenderGUI;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.security.account.AccountUtils;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.main.FormattingUtil;
import xyz.vergoclient.util.main.RenderUtils;
import xyz.vergoclient.util.main.RenderUtils2;
import xyz.vergoclient.util.main.ServerUtils;

import java.awt.*;

public class vergosenseWatermark implements OnEventInterface {

    private static final ResourceLocation VERGOSENSE_BACKGROUND_TEXTURE;

    static {
        VERGOSENSE_BACKGROUND_TEXTURE = new ResourceLocation("fuckafriendforfree.png");
    }

    @Override
    public void onEvent(Event e) {

        if(e instanceof EventRenderGUI) {
            drawVergosenseWatermark();
        }
    }

    private static void drawVergosenseWatermark() {
        JelloFontRenderer fr = FontUtil.comfortaaSmall;

        String vergoStr = "vergo" + EnumChatFormatting.GREEN + "sense" + EnumChatFormatting.WHITE + " | " + ServerUtils.getServerIP() + " | " + AccountUtils.account.username + "#" + FormattingUtil.formatUID();

        RenderUtils2.drawRect(2, 2, (float) (12 + fr.getStringWidth(vergoStr)), 18, new Color(0x434343).getRGB());
        RenderUtils2.drawRect(3f, 3f, (float) (10 + fr.getStringWidth(vergoStr)), 16, new Color(0x434343).darker().getRGB());
        RenderUtils.drawImg(VERGOSENSE_BACKGROUND_TEXTURE, 3f, 3f, (float) (10 + fr.getStringWidth(vergoStr)), 16);
        RenderUtils2.drawRect(5, 5, (float) (6 + fr.getStringWidth(vergoStr)), 12, new Color(0x434343).getRGB());
        RenderUtils2.drawRect(6f, 6f, (float) (4 + fr.getStringWidth(vergoStr)), 10, new Color(0x303030).darker().getRGB());

        fr.drawString(vergoStr, 8f, 9.5f, new Color(0xffffff).getRGB());
    }

}
