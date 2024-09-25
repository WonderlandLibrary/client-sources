package none.clickGui.configpanel;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import none.Client;
import none.utils.render.Colors;
import none.utils.render.TTFFontRenderer;

public class ConfigPanel extends GuiScreen{
	public TTFFontRenderer fontRenderer = Client.fm.getFont("BebasNeue");
	public GuiScreen old;
	
	public ArrayList<Slot> slot = new ArrayList<>();
	
	public ConfigPanel(GuiScreen old) {
		this.old = old;
		ClearandloadSlot();
	}
	
	public void ClearandloadSlot() {
		slot.clear();
		ArrayList<String> confignames = Client.instance.fileManager.getConfigList();
		if (!confignames.isEmpty()) {
			int StartX = 30;
			int StartY = 30;
			int addX = 100;
			int addY = 30;
			int count = 0;
			int ycount = 0;
			for (int i = 0; i < confignames.size(); i++) {
				double posX1 = StartX + (addX * count);
				double posY1 = StartY + (addY * ycount);
				String name = confignames.get(i);
				slot.add(new ConfigSlot(name, posX1, posY1, 80, 14));
				count++;
				if (count > 5) {
					ycount++;
					count = 0;
				}
				//Last do
				if (i == confignames.size() - 1) {
					int posX = StartX + (addX * count);
					int posY = StartY + (addY * ycount);
					slot.add(new AddOrSave(posX, posY, 80, 14));
					count++;
					if (count > 5) {
						ycount++;
						count = 0;
					}
					int posX2 = StartX + (addX * count);
					int posY2 = StartY + (addY * ycount);
					slot.add(new RemoveAndSave(posX2, posY2, 80, 14));
					count++;
					if (count > 5) {
						ycount++;
						count = 0;
					}
				}
			}
		}
	}
	
	@Override
	public void initGui() {
		buttonList.add(new CustomButton(-1, 3, height - 14, 100, 14, "Back"));
		super.initGui();
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case -1:
			mc.displayGuiScreen(old);
			break;
		default:
			break;
		}
		super.actionPerformed(button);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution res = new ScaledResolution(mc);
		Gui.drawRect(0, 0, res.getScaledWidth(), res.getScaledHeight(), Colors.getColor(Color.BLACK, 150));
		for (Slot slot : slot) {
			slot.drawScreen(mouseX, mouseY, partialTicks);
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		for (Slot slot : slot) {
			slot.mouseClicked(mouseX, mouseY, mouseButton);
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		for (Slot slot : slot) {
			slot.keyTyped(typedChar, keyCode);
		}
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
	}
}
