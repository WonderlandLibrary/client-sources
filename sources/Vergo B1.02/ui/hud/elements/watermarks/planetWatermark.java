package xyz.vergoclient.ui.hud.elements.watermarks;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.RenderUtils;

import static org.lwjgl.opengl.GL11.*;

public class planetWatermark implements OnEventInterface {
    @Override
    public void onEvent(Event e) {
        drawPlanetWatermark();
    }

    private void drawPlanetWatermark() {
        GlStateManager.pushMatrix();

        RenderUtils.drawImg(new ResourceLocation("Vergo/logo/512x512-transparent.png"), 3, 3, 50, 50);

        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

        // Enable Smooth
        glEnable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);

        RenderUtils.drawImg(new ResourceLocation("Vergo/logo/512x512-transparent.png"), 3, 3, 50, 50);

        // Disable Smooth
        glDisable(GL_LINE_SMOOTH);
        glHint(GL_LINE_SMOOTH_HINT, GL_DONT_CARE);

        GlStateManager.popMatrix();
    }
}
