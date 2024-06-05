package alphentus.gui.clickguisigma.settings;

import alphentus.init.Init;
import alphentus.mod.Mod;
import alphentus.mod.mods.hud.HUD;
import alphentus.mod.mods.visuals.ClickGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;

/**
 * @author avox | lmao
 * @since on 29/07/2020.
 */
public class VisibleButton {

    private final Mod mod;
    private int x, y, width, height;
    private final FontRenderer fontRenderer = Init.getInstance().fontManager.stem19;
    private HUD hud = Init.getInstance().modManager.getModuleByClass(HUD.class);

    public VisibleButton(Mod mod) {
        this.mod = mod;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

        String visible = "" + mod.isVisible();
        Init.getInstance().fontManager.myinghei22.drawStringWithShadow("Visible: " + visible.toUpperCase(), scaledResolution.getScaledWidth() / 2 - 140, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, hud.textColor.getRGB(), false);
    }


    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (isHovering(mouseX, mouseY) && mouseButton == 0) {
            this.mod.setVisible(!this.mod.isVisible());
        }

    }

    public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public boolean isHovering(int mouseX, int mouseY) {
        return mouseX > x && mouseX < x + width + 4 && mouseY > y && mouseY < y + height;
    }

}