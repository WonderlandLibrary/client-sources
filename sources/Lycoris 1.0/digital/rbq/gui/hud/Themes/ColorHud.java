package digital.rbq.gui.hud.Themes;

import org.lwjgl.opengl.GL11;
import digital.rbq.Lycoris;
import digital.rbq.gui.fontRenderer.FontManager;
import digital.rbq.gui.hud.HudRenderer;
import digital.rbq.gui.hud.Theme;
import digital.rbq.module.Module;
import digital.rbq.module.implement.Render.Hud;

public class ColorHud implements Theme {
    @Override
    public String getName() {
        return "Color";
    }

    @Override
    public void render(float newWidth, float newHeight) {
        GL11.glPushMatrix();

        float yIndex = 2 + HudRenderer.animationY;
        for (Module module : Lycoris.INSTANCE.getModuleManager().getModulesRender(Hud.isMinecraftFont ? mc.fontRendererObj : FontManager.normal2)) {
            String renderName = module.getDisplayText();

            yIndex = Rainbow.getYIndex(newWidth, newHeight, yIndex, renderName, Hud.arraylistColor1.getColorInt());
        }

        GL11.glPopMatrix();
    }

    @Override
    public void renderWatermark() {
        if (Hud.isMinecraftFont) {
            mc.fontRendererObj.drawStringWithShadow(Hud.name, 3, 3, Hud.watermarkColour.getColorInt());
        } else {
            FontManager.big.drawStringWithSuperShadow(Hud.name, 3, 2, Hud.watermarkColour.getColorInt());
        }
    }
}