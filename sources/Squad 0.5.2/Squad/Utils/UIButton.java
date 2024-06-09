package Squad.Utils;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

public class UIButton extends GuiButton
{
    private int fade;
    
    public UIButton(final int buttonId, final int x, final int y, final String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }
    
    public UIButton(final int buttonId, final int x, final int y, final int widthIn, final int heightIn, final String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
    }
    
    @Override
    public void drawButton(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
            if (!hovered) {
                if (this.fade != 100) {
                    this.fade += 5;
                }
            }
            else {
                if (this.fade <= 40) {
                    return;
                }
                if (this.fade != 70) {
                    this.fade -= 5;
                }
            }
            final Color a = new Color(255, 255, 255, this.fade);
            final FontRenderer var4 = mc.fontRendererObj;
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition - 90 + this.width, this.yPosition + this.height, a.getRGB());
            this.drawCenteredString(var4, this.displayString, this.xPosition - 45 + this.width / 2, this.yPosition + (this.height - 8) / 2, 16777215);
        }
    }
}