package alphentus.gui.buttons;

import alphentus.init.Init;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 06/08/2020.
 */
public class CustomButton extends GuiButton {

    public UnicodeFontRenderer fontRenderer = Init.getInstance().fontManager.myinghei25;

    public CustomButton (int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }

    @Override
    public void drawButton (Minecraft mc, int mouseX, int mouseY) {

        if (!visible)
            return;

        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

        RenderUtils.drawRoundedRect(this.xPosition, this.yPosition, width, height, 6, Init.getInstance().CLIENT_COLOR);
        if (hovered) {
            RenderUtils.drawRoundedRect(this.xPosition, this.yPosition, this.width, this.height,6, new Color(Init.getInstance().CLIENT_COLOR.getRed() - 1, Init.getInstance().CLIENT_COLOR.getGreen() - 4, Init.getInstance().CLIENT_COLOR.getBlue() - 6, 255));
        }

        this.fontRenderer.drawStringWithShadow(this.displayString, this.xPosition + this.width / 2 - this.fontRenderer.getStringWidth(this.displayString) / 2 + 0.5F, this.yPosition + this.height / 2 - this.fontRenderer.FONT_HEIGHT / 2 + 1F, -1, false);
    }

}