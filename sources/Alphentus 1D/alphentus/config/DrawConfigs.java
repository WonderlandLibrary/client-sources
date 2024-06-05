package alphentus.config;

import alphentus.init.Init;
import alphentus.mod.mods.hud.HUD;
import alphentus.utils.Test;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

/**
 * @author avox | lmao
 * @since on 07.08.2020.
 */
public class DrawConfigs {

    ScaledResolution sr;
    UnicodeFontRenderer fontRenderer;
    Config config;
    int x, y, width, height, center;
    HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    public DrawConfigs(Config config, int x, int y, int width) {
        this.config = config;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = 20;
        fontRenderer = Init.getInstance().fontManager.myinghei21;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks, Color elementColor, Color textColor) {
        sr = new ScaledResolution(Minecraft.getMinecraft());
        center = sr.getScaledWidth() / 2;

        Gui.drawRect(x, y, width, y + height, elementColor.getRGB());
        fontRenderer.drawStringWithShadow(config.getName(), center - fontRenderer.getStringWidth(config.getName()) / 2, y + fontRenderer.getStringHeight(config.getName()) / 4, textColor.getRGB(), false);
    }

    public void initGui() {
    }

    public boolean isHovered(float mouseX, float mouseY) {
        return mouseX > x && mouseX < width && mouseY > y && mouseY < y + height;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && isHovered(mouseX, mouseY)) {
            config.loadConfig();
        }
    }
}