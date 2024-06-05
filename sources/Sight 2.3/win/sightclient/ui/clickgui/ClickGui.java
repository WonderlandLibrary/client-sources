package win.sightclient.ui.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;
import win.sightclient.Sight;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.module.Category;
import win.sightclient.module.render.ClickGUIMod;

public class ClickGui extends GuiScreen {

	private ArrayList<Panel> panels = new ArrayList<Panel>();
	public static boolean dragging = false;
	
	public ClickGui() {
		int x = 3;
		int y = 5;
		int count = 0;
		for (Category c : Category.values()) {
			Panel p = new Panel(x, y, c);
			panels.add(p);
			x += p.getWidth() + 5;
			count++;
			if (count % 3 == 0) {
				y += 50;
				x = 3;
			}
		}
	}
	
	public void reload() {
		for (Panel p : this.panels) {
			p.reload();
		}
	}
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    	for (Panel p : this.panels) {
    		p.drawScreen(mouseX, mouseY);
    	}
    }
	
	@Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	for (Panel p : this.panels) {
    		p.mouseClicked(mouseX, mouseY, mouseButton);
    	}
    }
	
	@Override
    public void onGuiClosed()
    {
		Keyboard.enableRepeatEvents(false);
    }
	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for (Panel p : this.panels) {
			p.mouseReleased(mouseX, mouseY, state);
		}
    }
	
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    	for (Panel p : this.panels) {
    		p.keyTyped(typedChar, keyCode);
    	}
    	super.keyTyped(typedChar, keyCode);
    }
	
	public static Color getSecondaryColor(boolean setting) {
		return setting ? new Color(35, 35, 35) : new Color(50, 50, 50);
	}
	
	public static Color getPrimaryColor() {
		return new Color(ClickGUIMod.red.getValueFloat(), ClickGUIMod.green.getValueFloat(), ClickGUIMod.blue.getValueFloat());
	}
	
	private static TTFFontRenderer font;
	
	public static TTFFontRenderer getFont() {
		if (font == null) {
			font = Sight.instance.fm.getFont("SFUI 18");
		}
		return font;
	}
}
