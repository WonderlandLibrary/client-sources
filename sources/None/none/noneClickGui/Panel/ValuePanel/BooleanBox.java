package none.noneClickGui.Panel.ValuePanel;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import none.module.modules.render.ClientColor;
import none.utils.render.TTFFontRenderer;
import none.valuesystem.BooleanValue;
import none.valuesystem.Value;

public class BooleanBox extends ValueSlot{
	
	public BooleanValue value;
	
	public BooleanBox(BooleanValue value) {
		this.value = value;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		if (value.getObject()) {
			renderColor = Color.GREEN.getRGB();
		}else if (!value.getObject()) {
			renderColor = Color.RED.getRGB();
		}
		x = x + 3;
		y = y + 1;
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		Gui.drawRect(x - 1, y - 1, x + 13, y + 13, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		Gui.drawRect(x, y, x + 12, y + 12, renderColor);
		jigsawFont.drawStringWithShadow(value.getName(), x + 14, y, renderColor);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (!isCheckHovered(x, y)) return;
		if (button == 0 && isCheckHovered(x, y)) {
			value.setObject(!value.getObject());
		}
		super.mousePressed(button, x, y);
	}
	
	public boolean isCheckHovered(int mouseX, int mouseY) {
		return mouseX >= x + 1 && mouseX <= x + 12 && mouseY >= y + 2 && mouseY <= y + 13;
	}
}
