package win.sightclient.ui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import win.sightclient.Sight;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.utils.minecraft.RenderUtils;

public class MenuButton extends GuiButton {

	public MenuButton(int buttonId, int x, int y, String buttonText) {
		super(buttonId, x, y, buttonText);
	}
	
	public MenuButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, x, y, widthIn, heightIn, buttonText);
	}
	
	TTFFontRenderer font;

	@Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
        	this.hovered = mouseX > this.xPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height && mouseY > this.yPosition;
        	RenderUtils.drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? 5F : 3F, new Color(255, 100, 0).getRGB());
        	if (font == null) {
            	font = Sight.instance.fm.getFont("SFUI 18");
        	}
        	font.drawStringWithShadow(this.displayString, this.xPosition + (this.width / 2) - (font.getStringWidth(this.displayString) / 2), this.yPosition + (this.height / 2) - (font.getHeight(this.displayString) / 2), -1);
        }
    }
}
