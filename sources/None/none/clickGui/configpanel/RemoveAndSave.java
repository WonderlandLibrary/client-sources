package none.clickGui.configpanel;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import none.Client;
import none.clickGui.clickgui;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;

public class RemoveAndSave extends Slot{
	public TTFFontRenderer fontRenderer = Client.fm.getFont("BebasNeue");
	public int posX, posY, w, h;
	public GuiTextField recivetextgui = null;
	public RemoveAndSave(int posX, int posY, int w, int h) {
		this.posX = posX;
		this.posY = posY;
		this.w = w;
		this.h = h;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (recivetextgui == null) {
			recivetextgui = new GuiTextField(-99, mc.fontRendererObj, posX, posY + 5, w, h);
			recivetextgui.setVisible(false);
		}
		double posX = this.posX;
		if (isHover(mouseX, mouseY)) {
			posX += 10;
		}
		Gui.drawOutlineRGB((float)this.posX, (float)this.posY, (float)this.posX + (float)this.w, (float)this.posY + (float)this.h, 2, Colors.getColor(Color.BLACK, 210));
		fontRenderer.drawString("Remove Config", (float)((posX + posX + w) / 2) - (float)(fontRenderer.getStringWidth("AddOrSave") / 2), (float)((posY + posY + h) / 2) - (float)(fontRenderer.getHeight("AddOrSave") / 2), Color.WHITE.getRGB());
		if (recivetextgui != null) {
			recivetextgui.drawTextBox();
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (recivetextgui != null) {
			recivetextgui.mouseClicked(mouseX, mouseY, mouseButton);
		}
		
		if (!isHover(mouseX, mouseY)) {
			if (recivetextgui != null) {
				if (!recivetextgui.getText().isEmpty()) {
					ArrayList<String> list = Client.instance.fileManager.getConfigList();
					for (String str : list) {
						if (str.equalsIgnoreCase(recivetextgui.getText())) {							
							Client.instance.fileManager.RemoveConfig(str);
							Client.instance.clickgui.UpdateConfigDisplay();
						}
					}
				}
				recivetextgui.setFocused(false);
				recivetextgui.setVisible(false);
			}
			recivetextgui = null;
		}else {
			if (mouseButton == 0)
			if (recivetextgui != null) {
				recivetextgui.setFocused(true);
				recivetextgui.setVisible(true);
			}
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) {
		if (recivetextgui != null) {
			if (keyCode == Keyboard.KEY_RETURN) {
				if (!recivetextgui.getText().isEmpty()) {
					ArrayList<String> list = Client.instance.fileManager.getConfigList();
					for (String str : list) {
						if (str.equalsIgnoreCase(recivetextgui.getText())) {							
							Client.instance.fileManager.RemoveConfig(str);
							Client.instance.clickgui.UpdateConfigDisplay();
						}
					}
				}
				recivetextgui.setFocused(false);
				recivetextgui.setVisible(false);
			}else {
				recivetextgui.textboxKeyTyped(typedChar, keyCode);
			}
		}
		super.keyTyped(typedChar, keyCode);
	}
	
	public boolean isHover(double mouseX, double mouseY) {
		return mouseX >= posX && mouseX <= posX + w && mouseY >= posY && mouseY <= posY + h;
	}
}
