package none.clickGui.modulepanel;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import none.Client;
import none.event.events.EventChat;
import none.fontRenderer.sigma.FontManager;
import none.module.Category;
import none.module.modules.render.ClientColor;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;

public class CategoryPanel {
	public ArrayList<ModulePanel> modulespanel = new ArrayList<>();
	
	public TTFFontRenderer fontRenderer = Client.fm.getFont("BebasNeue");
	public int x,y,w,h;
	public boolean ishover = false;
	public Category category;
	public CategoryPanel(Category category, int x, int w) {
		this.category = category;
		this.x = x;
		this.w = w;
		setup();
	}
	
	public boolean extended = false, canShow = false;
	public int opening = 0, timer = 0;
	
	public Category getCategory() {
		return category;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int renderColor = ClientColor.getColor();
		if (ClientColor.rainbow.getObject()) {
			renderColor = ClientColor.rainbow(10000);
		}
		
		if (isHovered(mouseX, mouseY)) {			
			renderColor = ClientColor.getColor();
			canShow = true;
			if (opening > 0) {
				opening -= opening / 4 + 1;
			}
			
			if (opening < 4) {
				opening = 0;
				extended = !extended;
			}
		}else {
			if (opening < 50) {
				opening += opening / 4 + 1;
			}
			
			if (opening > 50) {
				opening = 50;
				canShow = false;
			}
		}
		y = 10;
		String title = Character.toUpperCase(category.name().toLowerCase().charAt(0)) + category.name().toLowerCase().substring(1);
		h = (int)fontRenderer.getHeight(title);
		Gui.drawOutineRect(x, y, x + w, y + h + 7, 2, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 200), renderColor);
		fontRenderer.drawString(title, x + (w/2) - ((int)fontRenderer.getStringWidth(title) / 2), y + (h/2), renderColor);
		
		if (!this.modulespanel.isEmpty() && canShow) {
			int startY = y + h + 7 + opening;
			for (ModulePanel m : this.modulespanel) {
				m.x = x;
				m.y = startY;
				m.drawScreen(mouseX, mouseY, partialTicks);
				startY += m.h + 4;
			}
		}
	}
	
	public void onClick(int mouseX, int mouseY, int mode) {
		if (!isHovered(mouseX, mouseY)) return;
		for (ModulePanel mod : modulespanel) {
			mod.onClick(mouseX, mouseY, mode);
		}
	}
	
	public void onReleased(int mouseX, int mouseY, int mode) {
		for (ModulePanel mod : modulespanel) {
			mod.onReleased(mouseX, mouseY, mode);
		}
	}
	
	public void onClickMove(int mouseX, int mouseY) {
		for (ModulePanel mod : modulespanel) {
			mod.onClickMove(mouseX, mouseY);
		}
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + w;
	}
	
	public void setup() {
		
	}
}
