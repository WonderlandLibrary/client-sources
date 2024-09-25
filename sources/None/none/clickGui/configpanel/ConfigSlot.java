package none.clickGui.configpanel;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import none.Client;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;

public class ConfigSlot extends Slot{
	public TTFFontRenderer fontRenderer = Client.fm.getFont("BebasNeue");
	public String configname;
	public double posX, posY, w, h;
	public ConfigSlot(String configname, double posX, double posY, double w, double h) {
		this.configname = configname;
		this.posX = posX;
		this.posY = posY;
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		double posX = this.posX;
		if (isHover(mouseX, mouseY)) {
			posX += 10;
		}
		Gui.drawOutlineRGB((float)this.posX, (float)this.posY, (float)this.posX + (float)this.w, (float)this.posY + (float)this.h, 2, Colors.getColor(Color.BLACK, 210));
		fontRenderer.drawString(configname, (float)((posX + posX + w) / 2) - (float)(fontRenderer.getStringWidth(configname) / 2), (float)((posY + posY + h) / 2) - (float)(fontRenderer.getHeight(configname) / 2), Color.WHITE.getRGB());
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		onClick(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	public void onClick (double mouseX, double mouseY, int button) {
		if (button == 0) {
			if (isHover(mouseX, mouseY)) {
				Client.instance.fileManager.loadConfig(configname);
			}
		}
	}
	
	public boolean isHover(double mouseX, double mouseY) {
		return mouseX >= posX && mouseX <= posX + w && mouseY >= posY && mouseY <= posY + h;
	}
}
