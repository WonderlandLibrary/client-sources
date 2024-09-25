package none.clickGui.valuepanel;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiTextField;
import none.Client;
import none.module.modules.render.ClientColor;
import none.noneClickGui.Utils;
import none.noneClickGui.ValueChangeListener;
import none.valuesystem.NumberValue;

public class NumberSlot extends VSlot{
	
	public boolean dragging = false;
	public NumberValue v;
	float current;
	public float min, max;
	private int maxw = 100;
	private NumberType numberType;
	private ValueChangeListener<Number> listener;
	public boolean changing = false, onGui = false;
	public GuiTextField textfield;
	
	public NumberSlot(NumberValue v, float current, float min, float max, NumberType type) {
		this.v = v;
		this.current = current;
		this.min = min;
		this.max = max;
		this.numberType = type;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Client.fm.getFont("BebasNeue");
		if (textfield == null && onGui) {
			textfield = new GuiTextField(-999, mc.fontRendererObj, this.x, this.y - 1, 100, 15);
			textfield.setEnableBackgroundDrawing(true);
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
	//			if (v.getObject() instanceof Float || v.getObject() instanceof Double) {
	//				Gui.drawOutineRect(x, y + 19, x + fontRenderer.getStringWidth("-0.01"), y + 21 + fontRenderer.getHeight("-0.01"), 1, Color.BLACK.getRGB(), renderColor);
	//				fontRenderer.drawString("-0.01", x, y + 20, renderColor);
	//			}else if (v.getObject() instanceof Integer && v.getMin().intValue() == 0 && v.getMax().intValue() == 100) {
	//				//percent value
	//				Gui.drawOutineRect(x, y + 19, x + fontRenderer.getStringWidth("-1"), y + 21 + fontRenderer.getHeight("-1"), 1, Color.BLACK.getRGB(), renderColor);
	//				fontRenderer.drawString("-1", x, y + 20, renderColor);
	//			}else if (v.getObject() instanceof Integer) {
	//				Gui.drawOutineRect(x, y + 19, x + fontRenderer.getStringWidth("-1"), y + 21 + fontRenderer.getHeight("-1"), 1, Color.BLACK.getRGB(), renderColor);
	//				fontRenderer.drawString("-1", x, y + 20, renderColor);
	//			}
			}
			int sliderWidth = 4;
			double sliderPos = (current - min) / (max - min) * (maxw - sliderWidth);
			this.width = maxw + 10;
			String displayval = numberType.getFormatter().apply(current);
			if (onGui) {
				displayval = "Insert Value";
			}
			Gui.drawOutineRect(x, y - 2, x + maxw, y + 12, 1, Color.BLACK.getRGB(), renderColor2);
			Gui.drawRectRGB(x, y + 13, x + (int)sliderPos, y + 15, renderColor2);
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
			if (onGui && textfield != null && !textfield.getText().isEmpty()) {
				double oldValue = current;
				try {
					float newValue = Float.parseFloat(textfield.getText());
					String str = numberType.formatter.apply(newValue);
					newValue = Float.parseFloat(str);
					boolean change = true;
					if (oldValue != newValue && listener != null) {
						if (newValue > max)
							newValue = max;
						if (newValue < min)
							newValue = min;
		                change = listener.onValueChange(newValue);
		            }
		            if (change) {
		            	current = newValue;
		            }
				}catch (NumberFormatException e) {
					e.printStackTrace();
					Client.instance.notification.show(Client.notification("ClickGui", e.getMessage()));
				}catch (Exception e) {
					e.printStackTrace();
					Client.instance.notification.show(Client.notification("ClickGui", e.getMessage()));
				}
				textfield.setFocused(false);
			}
			onGui = false;
			textfield = null;
			return;
		}
		
		if (textfield != null && onGui) {
			textfield.mouseClicked(x, y, button);
		}
		
		if (button == 0) {
//			dragging = true;
//			changing = true;
//
//            updateValue(x, y);
			onGui = true;
		}
		super.mousePressed(button, x, y);
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		dragging = false;
//		if (changing) {
//            changing = false;
//            updateValue(x, y);
//        }
		super.mouseReleased(button, x, y);
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		if (!isHovered(x, y) 
//				&& changing
				) return;
//        updateValue(x, y);
		super.mouseMoved(x, y);
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x + 4 && mouseX <= x + 4 + this.maxw && mouseY >= y && mouseY <= y + 12;
	}
	
	public void setValue(float current) {
        this.current = current;
    }
	
	public void setListener(ValueChangeListener<Number> listener) {
        this.listener = listener;
    }
	
	public NumberType getNumberType() {
		return numberType;
	}
	
	public float getCurrent() {
		return current;
	}
	
	private void updateValue(int x, int y) {
        if (changing) {
            double oldValue = current;
            float newValue = Math.max(Math.min((x - this.x) / (float) maxw * (max - min) + min, max), min);
            
            boolean change = true;
            if (oldValue != newValue && listener != null) {
                change = listener.onValueChange(newValue);
            }
            if (change) {
            	current = newValue;
            }
        }

    }
	
	public enum NumberType {
        PERCENT(number -> String.format(Locale.ENGLISH, "%.1f%%", number.floatValue())),
        TIME(number -> Utils.formatTime(number.longValue())),
        DECIMAL(number -> String.format(Locale.ENGLISH, "%.2f", number.floatValue())),
        INTEGER(number -> Long.toString(number.longValue()));

        private Function<Number, String> formatter;

        NumberType(Function<Number, String> formatter) {
            this.formatter = formatter;
        }

        public Function<Number, String> getFormatter() {
            return formatter;
        }
    }
	
	@Override
	public void onClose() {
		onGui = false;
		textfield = null;
		super.onClose();
	}
	
	@Override
	public void onKey(char typedChar, int keyCode) {
		if (onGui && textfield != null)
		if (keyCode == Keyboard.KEY_ESCAPE) {
			textfield = null;
			onGui = false;
		}else {
			List<Character> charer = Arrays.asList('1','2','3','4','5','6','7','8','9','0','.', '-');
			if (textfield.isFocused()) {
				if (keyCode == Keyboard.KEY_RETURN && !textfield.getText().isEmpty()) {
					double oldValue = current;
					try {
						float newValue = Float.parseFloat(textfield.getText());
						String str = numberType.formatter.apply(newValue);
						newValue = Float.parseFloat(str);
						boolean change = true;
						if (oldValue != newValue && listener != null) {
							if (newValue > max)
								newValue = max;
							if (newValue < min)
								newValue = min;
			                change = listener.onValueChange(newValue);
			            }
			            if (change) {
			            	current = newValue;
			            }
					}catch (NumberFormatException e) {
						e.printStackTrace();
						Client.instance.notification.show(Client.notification("ClickGui", e.getMessage()));
					}catch (Exception e) {
						e.printStackTrace();
						Client.instance.notification.show(Client.notification("ClickGui", e.getMessage()));
					}
					textfield.setFocused(false);
					textfield = null;
					onGui = false;
				}else if (charer.contains(typedChar) || keyCode == Keyboard.KEY_BACK) {
					textfield.textboxKeyTyped(typedChar, keyCode);
				}
			}
		}
		super.onKey(typedChar, keyCode);
	}
	
	@Override
	public void onUpdate() {
		if (onGui && textfield != null)
		textfield.updateCursorCounter();
	}
}