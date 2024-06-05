package win.sightclient.ui.altmanager.gui;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import win.sightclient.ui.altmanager.AltManager;

public class AltButton extends GuiButton {

	private int alt;
	private boolean selected = false;
	private int originalY;
	
	private GuiAltManager parent;
	
    public AltButton(int x, int y, int alt, GuiAltManager parent) {
    	super(0, x, y, "");
    	this.alt = alt;
    	this.parent = parent;
    	this.originalY = y;
    }
    
    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    	if (!this.parent.isAltInArea(this.yPosition)) {
    		return;
    	}
    	this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        
    	Color color = new Color(0, 0, 0, 150);
    	if (this.hovered) {
    		color = new Color(0, 0, 0, 200);
    	}
    	Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, color.getRGB());
    	if (this.selected) {
    		Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + 2, this.yPosition + this.height, new Color(150, 150, 150).getRGB());
    		Gui.drawRect(this.xPosition + this.width - 2, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(150, 150, 150).getRGB());
    	}
    	mc.fontRendererObj.drawStringWithShadow(AltManager.getAlts().get(alt).getUser(), this.xPosition + (this.width / 2) - (mc.fontRendererObj.getStringWidth(AltManager.getAlts().get(alt).getUser()) / 2), this.yPosition + (this.height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2), -1);
    }
    
    public void setSelected(boolean selected) {
    	this.selected = selected;
    }
    
    public boolean isSelected() {
    	return this.selected;
    }
    
    public int getOrigY() {
    	return this.originalY;
    }
    
    public int getAlt() {
    	return this.alt;
    }
    
    public void mouseClicked(int x, int y) {
    	if (this.selected) {
    		this.parent.login(AltManager.getAlts().get(alt));
    	}
    	this.parent.setSelected(this.alt);
    }
}
