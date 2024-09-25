package none.noneClickGui.Panel.ValuePanel;

import none.module.modules.render.ClientColor;
import none.valuesystem.ModeValue;

public class ModeBox extends ValueSlot{
	
	public ModeValue value;
	
	public ModeBox(ModeValue value) {
		this.value = value;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		y = y + 4;
		width = (int) jigsawFont.getStringWidth(value.getName() + " : " + value.getSelected());
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		jigsawFont.drawStringWithShadow(value.getName() + " : " + value.getSelected(), x, y, renderColor);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
		if (button == 0) {
			if (value.getObject() > 0) {
				value.setObject(value.getObject() - 1);
			}else if (value.getObject() == 0) {
				value.setObject(value.getModes().length - 1);
			}
		}else if (button == 1) {
			if (value.getObject() < value.getModes().length - 1) {
				value.setObject(value.getObject() + 1);
			}else if (value.getObject() == value.getModes().length - 1) {
				value.setObject(0);
			}
		}
		super.mousePressed(button, x, y);
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		super.mouseReleased(button, x, y);
	}
}
