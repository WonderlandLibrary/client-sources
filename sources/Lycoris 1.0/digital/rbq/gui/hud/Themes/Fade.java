package digital.rbq.gui.hud.Themes;

import org.lwjgl.opengl.GL11;
import digital.rbq.Lycoris;
import digital.rbq.gui.fontRenderer.FontManager;
import digital.rbq.gui.hud.HudRenderer;
import digital.rbq.gui.hud.Theme;
import digital.rbq.module.Module;
import digital.rbq.module.implement.Render.Hud;
import digital.rbq.utility.ColorUtils;
import digital.rbq.utility.WorldRenderUtils;

import java.awt.*;

public class Fade implements Theme {
    @Override
    public String getName() {
        return "Fade";
    }

    public static float getYIndex(float newWidth, float newHeight, float yIndex, String renderName) {
        float x = Theme.renderBackground(newWidth, yIndex, renderName, mc.fontRendererObj.getStringWidth(renderName), mc.getRenderViewEntity());

        int color = ColorUtils.fadeBetween(Hud.arraylistColor1.getColorInt(), Hud.arraylistColor1.getColor().darker().darker().darker().getRGB(), (int) yIndex * (Hud.fadeDirection.isCurrentMode("Up") ? 7 : -7));

        if (Hud.colorbar.getValue()) {
            WorldRenderUtils.drawRects(WorldRenderUtils.getScaledResolution().getScaledWidth() - 1.5, yIndex + (10 - Hud.offset.getValue()), WorldRenderUtils.getScaledResolution().getScaledWidth(), yIndex + 10, new Color(color));
        }

        if (Hud.isMinecraftFont) {
            mc.fontRendererObj.drawStringWithShadow(renderName, x, yIndex + 1, color);
        } else {
            FontManager.normal2.drawStringWithShadow(renderName, x, yIndex, color);
        }

        yIndex += Hud.offset.getValue();
        return yIndex;
    }

    @Override
    public void render(float newWidth, float newHeight) {
        GL11.glPushMatrix();
        // sort
        float yIndex = 2 + HudRenderer.animationY;

        for (Module module : Lycoris.INSTANCE.getModuleManager().getModulesRender(Hud.isMinecraftFont ? mc.fontRendererObj : FontManager.normal2)) {
            String renderName = module.getDisplayText();
            yIndex = Fade.getYIndex(newWidth, newHeight, yIndex, renderName);
        }
        GL11.glPopMatrix();
    }

    @Override
    public void renderWatermark() {
        int color = ColorUtils.fadeBetween(Hud.arraylistColor1.getColorInt(), Hud.arraylistColor1.getColor().darker().darker().darker().getRGB());

        if (Hud.isMinecraftFont) {
            mc.fontRendererObj.drawStringWithShadow(Hud.name, 3, 3, color);
        } else {
            FontManager.big.drawStringWithSuperShadow(Hud.name, 3, 2, color);
        }
    }
}