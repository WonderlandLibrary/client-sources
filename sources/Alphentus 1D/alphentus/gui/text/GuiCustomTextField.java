package alphentus.gui.text;

import alphentus.init.Init;
import alphentus.utils.GLUtil;
import alphentus.utils.RenderUtils;
import alphentus.utils.fontrenderer.UnicodeFontRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @author avox | lmao
 * @since on 05/08/2020.
 */
public class GuiCustomTextField extends GuiTextField {

    private final UnicodeFontRenderer unicodeFontRenderer = Init.getInstance().fontManager.myinghei25;

    public GuiCustomTextField (int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
        super(componentId, fontrendererObj, x, y, par5Width, par6Height);
    }

    @Override
    public void drawTextBox () {

        final boolean widerThanWidth = unicodeFontRenderer.getStringWidth(getText()) > width - 10;

        final int offsetX = (int) (widerThanWidth ? (unicodeFontRenderer.getStringWidth((getText())) - width + 12) : 0);

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GLUtil.makeScissorBox(xPosition, yPosition, xPosition + width, yPosition + height);

            RenderUtils.relativeRect(xPosition, yPosition + height - 2, xPosition + width, yPosition + height - 1F, isFocused() ? new Color(202,202,202,255).getRGB(): new Color(209,209,209,255).getRGB());

        if (getText().length() > 0) {
            String text = (getTextUntrimmed());
            if (getCursorPosition() != getTextUntrimmed().length()) {
                text = text.substring(0, getCursorPosition()).concat(this.cursorCounter / 6 % 2 == 0 ? "_" : "  ").concat(text.substring(getCursorPosition(), text.length()));
            }
            unicodeFontRenderer.drawStringWithShadow(text, xPosition + 5 - offsetX, yPosition + height / 2 - unicodeFontRenderer.FONT_HEIGHT / 2, isFocused() ? new Color(76,76,76,255).getRGB() : new Color(209,209,209,255).getRGB(), false);
        } else {
            unicodeFontRenderer.drawStringWithShadow(getTextPreview(), xPosition + 5 - offsetX, yPosition + height / 2 - unicodeFontRenderer.FONT_HEIGHT / 2, isFocused() ? new Color(76,76,76,255).getRGB() : new Color(209,209,209,255).getRGB(), false);
        }

        if (this.cursorCounter / 6 % 2 == 0 && isFocused()) {
            if (getCursorPosition() == getTextUntrimmed().length()) {
                unicodeFontRenderer.drawStringWithShadow("_", xPosition + 4 - offsetX + unicodeFontRenderer.getStringWidth((getText())), yPosition + height / 2 - unicodeFontRenderer.FONT_HEIGHT / 2, Color.white.getRGB());
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

    }

    @Override
    public boolean textboxKeyTyped (char p_146201_1_, int p_146201_2_) {
        return super.textboxKeyTyped(p_146201_1_, p_146201_2_);
    }

    @Override
    public void mouseClicked (int p_146192_1_, int p_146192_2_, int p_146192_3_) {
        super.mouseClicked(p_146192_1_, p_146192_2_, p_146192_3_);
    }

    @Override
    public String getText () {
        return super.getText().trim();
    }

    public String getTextUntrimmed () {
        return super.getText();
    }


}
