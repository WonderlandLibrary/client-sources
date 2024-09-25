package none.noneClickGui.Panel;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import none.event.events.EventChat;
import none.module.Module;
import none.module.modules.render.ClientColor;
import none.noneClickGui.NoneClickGui;
import none.utils.render.TTFFontRenderer;

public class ModulePanel {
	private TTFFontRenderer jigsawFont;
	public Module mod;
	public Panel panel;
	public int x, y, w, h;
	public boolean extended;
	
	public int StrX;
	
	public ModulePanel(Module m, Panel panel, TTFFontRenderer font, boolean extended) {
		this.jigsawFont = font;
		this.mod = m;
		this.panel = panel;
		this.h = (int) jigsawFont.getHeight(mod.getName()) + 1;
//		this.w = (int) jigsawFont.getStringWidth(mod.getName());
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int renderColorBG = java.awt.Color.BLACK.getRGB();
		if (isHovered(mouseX, mouseY)) {
			renderColorBG = Integer.MIN_VALUE;
		}else if (mod.isEnabled()) {
			renderColorBG = Color.WHITE.getRGB();
		}
		Gui.drawRect(x - 1, y - 1, x + w + 1, y + h + 1, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		if (renderColorBG == Integer.MIN_VALUE) {
			Gui.drawRectRGB(x, y, x + w, y + h, renderColorBG);
		}else {
			Gui.drawRect(x, y, x + w, y + h, renderColorBG);
		}
		jigsawFont.drawStringWithShadow(mod.getName(), StrX - ((int)jigsawFont.getStringWidth(mod.getName()) / 2), y, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
	}
	
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
        if (button == 0) {
        	mod.toggle();
        }else if (button == 1) {
        	boolean b = !this.extended;
        	this.extended = b;
        	NoneClickGui.valuemenu.Clear();
        	NoneClickGui.valuemenu.setModule(mod);
        	NoneClickGui.valuemenu.update();
        }
    }
	
	public void mouseReleased(int button, int x, int y) {
		if (!isHovered(x, y)) return;
    }
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
}
