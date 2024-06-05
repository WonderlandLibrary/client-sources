package xyz.vergoclient.ui.hud.elements.watermarks;

import net.minecraft.client.renderer.GlStateManager;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.ui.fonts.FontUtil;
import xyz.vergoclient.ui.fonts.JelloFontRenderer;
import xyz.vergoclient.util.main.ColorUtils;

import java.awt.*;

public class textWatermark implements OnEventInterface {


    @Override
    public void onEvent(Event e) {
        renderTextWatermark();
    }

    private void renderTextWatermark() {
        GlStateManager.pushMatrix();

        JelloFontRenderer fr = FontUtil.comfortaaHuge;
        JelloFontRenderer fr2 = FontUtil.comfortaaSmall;

        String clientName = "Vergo";
        String clientBuild = Vergo.version;


        final int textColour = ColorUtils.fadeBetween(new Color(108, 51, 217).getRGB(), new Color(210, 8, 62).getRGB(), 250);

        fr.drawString(clientName, 3, 5, textColour);
        fr2.drawString(clientBuild, 5 + fr.getStringWidth(clientName), 12, textColour);

        GlStateManager.popMatrix();
    }
}
