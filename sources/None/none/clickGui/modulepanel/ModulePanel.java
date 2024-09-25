package none.clickGui.modulepanel;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import none.Client;
import none.clickGui.clickgui;
import none.event.events.EventChat;
import none.module.Module;
import none.module.modules.render.ClientColor;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;

public class ModulePanel {
	public TTFFontRenderer fontRenderer;
	
	public Module mod;
	public CategoryPanel categoryPanel;
	
	public int x,y,w,h;
	
	public ModulePanel(Module mod, CategoryPanel categoryPanel) {
		this.mod = mod;
		this.categoryPanel = categoryPanel;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Client.fm.getFont("BebasNeue");
		int renderColor = ClientColor.getColor();
		if (ClientColor.rainbow.getObject()) {
			renderColor = ClientColor.rainbow(10000);
		}
		
		if (isHovered(mouseX, mouseY)) {
			x+= 4;
			renderColor = ClientColor.getColor();
		}
		String modname = mod.getName();
		if (mod.isEnabled()) {
			modname = mod.getName() + "(Enabled)";
		}
		w = categoryPanel.w;
		h = categoryPanel.h + 2;
		Gui.drawOutineRect(x + 4, y + 3, x + w - 4, y + h + 6, 1, Colors.getColor(Color.BLACK.getRed(), Color.BLACK.getGreen(), Color.BLACK.getBlue(), 200), renderColor);
		fontRenderer.drawString(modname, x + (w/2) - ((int)fontRenderer.getStringWidth(modname) / 2), y + (h/2) + 1, renderColor);
	}
	
	public void onClick(int mouseX, int mouseY, int mode) {
		if (!isHovered(mouseX, mouseY)) return;
		if (mode == 0) {
			mod.toggle();
		}else if (mode == 1) {
			clickgui.valuePanel.p.clear();
			clickgui.valuePanel.page.clear();
			clickgui.valuePanel.setModule(mod);
			clickgui.valuePanel.setCurrentPage(0);
			clickgui.valuePanel.update();
		}
	}
	
	public void onReleased(int mouseX, int mouseY, int mode) {
		
	}
	
	public void onClickMove(int mouseX, int mouseY) {
		
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
}
