package dev.darkmoon.client.ui.menu.widgets;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.Direction;
import dev.darkmoon.client.utility.render.animation.impl.DecelerateAnimation;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;

public class CustomButton extends GuiButton {
    public CustomButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

          //  RenderUtility.drawRoundedRect(x, y, width, height, 5, new Color(30, 30, 30).getRGB());

            if (this.hovered) {
           //     RenderUtility.drawRoundedRect(x + 1, y + 1, width - 2, height - 2, 4, new Color(30, 30, 30).getRGB());
            } else {
                Fonts.mntsb14.drawCenteredString(this.displayString, this.x + this.width / 2f, this.y + (this.height - 8) / 2f + 1, -1);
            }
        }
    }
}