package none.clickGui.valuepanel;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import none.Client;
import none.module.modules.render.ClientColor;
import none.valuesystem.ModeValue;

public class ModeSlot extends VSlot{
	
	public ModeValue mode;
	
	private boolean extended = false, showing = false;
	private int opening = 0;
	public ModeSlot(ModeValue mode) {
		this.mode = mode;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Client.fm.getFont("BebasNeue");
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		int x = this.x;
		int renderColor2 = renderColor;
		if (isHovered(mouseX, mouseY)) {
			x += 4;
			showing = true;
			opening = 10;
			renderColor2 = ClientColor.getColor();
		}else {
			if (opening > 0) {
				opening -= opening / 4 + 1;
			}
			
			if (opening == 0) {
				showing = false;
			}
		}
		if (showing) {
			Gui.drawOutineRect((x - opening) + 10, ((y - opening) + 10) + fontRenderer.getHeight(mode.getSelected()), ((x + opening) - 10) + (int)fontRenderer.getStringWidth(mode.getName() + ":" + mode.getSelected()), ((y + opening) - 10) + fontRenderer.getHeight(mode.getSelected()) * (mode.getModes().length + 1), 1, Color.BLACK.getRGB(), renderColor2);
			String[] modes = mode.getModes();
			for (int i = 0; i < modes.length; i++) {
				fontRenderer.drawString(modes[i], mode.getSelected().equalsIgnoreCase(modes[i]) ? x + 4 : x, y + (fontRenderer.getHeight(mode.getSelected()) * (i + 1)), mode.getSelected().equalsIgnoreCase(modes[i]) ? renderColor2 : renderColor);
			}
		}
		this.width = (int)fontRenderer.getStringWidth(mode.getName() + ":" + mode.getSelected()) + 10;
		fontRenderer.drawString(mode.getName() + ":" + mode.getSelected(), x + 4, y, renderColor2);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
		if (button == 0) {
			if (mode.getObject() > 0) {
				mode.setObject(mode.getObject() - 1);
			}else if (mode.getObject() == 0) {
				mode.setObject(mode.getModes().length - 1);
			}
		}else if (button == 1) {
			if (mode.getObject() < mode.getModes().length - 1) {
				mode.setObject(mode.getObject() + 1);
			}else if (mode.getObject() == mode.getModes().length - 1) {
				mode.setObject(0);
			}
		}
		super.mousePressed(button, x, y);
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		super.mouseMoved(x, y);
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x + 4 && mouseX <= x + 4 + this.width && mouseY >= y && mouseY <= y + 12;
	}
}
