package none.clickGui.valuepanel;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import none.Client;
import none.clickGui.valuepanel.NumberSlot.NumberType;
import none.module.modules.render.ClientColor;
import none.valuesystem.NumberValue;
import none.valuesystem.StringValue;

public class StringSlot extends VSlot{
	public StringValue v;
	public boolean changing = false, onGui = false;
	public GuiTextField textfield;
	public StringSlot(StringValue v) {
		this.v = v;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Client.fm.getFont("BebasNeue");
		if (textfield == null && onGui) {
			textfield = new GuiTextField(-999, mc.fontRendererObj, this.x, this.y - 1, 100, 15);
			textfield.setEnableBackgroundDrawing(true);
			textfield.setText(v.getObject());
			textfield.setFocused(true);
		}
		
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		try {
			int x = this.x;
			if (textfield != null && onGui) {
				textfield.xPosition = this.x;
			}
			int renderColor2 = renderColor;
			if (isHovered(mouseX, mouseY)) {
				x += 4;
				if (textfield != null && onGui) {
					textfield.xPosition += 4;
				}
				renderColor2 = ClientColor.getColor();
			}
			String displayval = v.getObject();
			if (displayval.length() > 14) {
				displayval = displayval.substring(0, 14) + "...";
			}
			if (onGui) {
				displayval = "Insert Value";
			}
			this.width = (int) (fontRenderer.getStringWidth(v.getName() + ":" + displayval) + 10);
			Gui.drawOutineRect(x, y - 2, x + fontRenderer.getStringWidth(v.getName() + ":" + displayval), y + 12, 1, Color.BLACK.getRGB(), renderColor2);
			fontRenderer.drawString(v.getName() + ":" + displayval, x + 4, y, renderColor2);
			if (onGui)
				textfield.drawTextBox();
		}catch (Exception e) {
			e.printStackTrace();
			Client.instance.notification.show(Client.notification("ClickGui", e.getMessage()));
		}
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) {
			if (onGui && textfield != null)
			if (!textfield.getText().isEmpty()) {
				v.setObject(textfield.getText());
			}
			textfield = null;
			onGui = false;
			return;
		}
		if (button == 0) {
			onGui = true;
			if (textfield != null)
			textfield.mouseClicked(x, y, button);
		}
		super.mousePressed(button, x, y);
	}
	
	@Override
	public void onKey(char typedChar, int keyCode) {
		if (onGui && textfield != null)
		if (keyCode == Keyboard.KEY_RETURN) {
			if (!textfield.getText().isEmpty()) {
				v.setObject(textfield.getText());
			}
			textfield = null;
			onGui = false;
			return;
		}else {
			textfield.textboxKeyTyped(typedChar, keyCode);
		}
		super.onKey(typedChar, keyCode);
	}
	
	@Override
	public void onUpdate() {
		if (onGui && textfield != null) {
			textfield.updateCursorCounter();
		}
		super.onUpdate();
	}
	
	@Override
	public void onClose() {
		onGui = false;
		textfield = null;
		super.onClose();
	}
}
