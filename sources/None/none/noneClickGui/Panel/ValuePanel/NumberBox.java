package none.noneClickGui.Panel.ValuePanel;

import java.awt.Color;
import java.util.Locale;
import java.util.function.Function;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import none.module.modules.render.ClientColor;
import none.noneClickGui.ValueChangeListener;
import none.utils.MathUtils;
import none.valuesystem.NumberValue;
import none.noneClickGui.Utils;;

public class NumberBox extends ValueSlot{
	
	public boolean dragging;
	public String name;
	float current;
	public float min, max;
	private NumberType numberType;
	private NumberValue v;
	private ValueChangeListener<Number> listener;
	private boolean changing = false;
	
	public NumberBox(String name, float current, float min, float max, NumberType type, NumberValue v) {
		this.name = name;
		this.current = current;
		this.min = min;
		this.max = max;
		this.numberType = type;
		this.v = v;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		int sliderWidth = 4;
		width = width - 2;
        double sliderPos = (current - min) / (max - min) * (width - sliderWidth);
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		Gui.drawRect(x + 1, y + 3, x + width, y + (int)jigsawFont.getHeight(name) + 7, renderColor);
		Gui.drawRect(x + (int)sliderPos + 2, y + 4, x + 2, y + (int)jigsawFont.getHeight(name) + 6, Color.BLACK.getRGB());
		String displayval = numberType.getFormatter().apply(current);
		jigsawFont.drawStringWithShadow(name + " : " + displayval, x + 3, y + 5, renderColor);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
		if (button == 0) {
			dragging = true;
			changing = true;

            updateValue(x, y);
		}
		super.mousePressed(button, x, y);
	}
	
	@Override
	public void mouseReleased(int button, int x, int y) {
		dragging = false;
		if (changing) {
            changing = false;
            updateValue(x, y);
        }
		super.mouseReleased(button, x, y);
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		if (!isHovered(x, y)) return;
        updateValue(x, y);
		super.mouseMoved(x, y);
	}
	
	private void updateValue(int x, int y) {
        if (changing) {
            double oldValue = current;
            float newValue = Math.max(Math.min((x - this.x) / (float) width * (max - min) + min, max), min);
            
            boolean change = true;
            if (oldValue != newValue && listener != null) {
                change = listener.onValueChange(newValue);
            }
            if (change) {
            	current = newValue;
            }
        }

    }
	
	public void setValue(float current) {
        this.current = current;
    }
	
	public void setListener(ValueChangeListener<Number> listener) {
        this.listener = listener;
    }
	
	public enum NumberType {
        PERCENT(number -> String.format(Locale.ENGLISH, "%.1f%%", number.floatValue())),
        TIME(number -> Utils.formatTime(number.longValue())),
        DECIMAL(number -> String.format(Locale.ENGLISH, "%.4f", number.floatValue())),
        INTEGER(number -> Long.toString(number.longValue()));

        private Function<Number, String> formatter;

        NumberType(Function<Number, String> formatter) {
            this.formatter = formatter;
        }

        public Function<Number, String> getFormatter() {
            return formatter;
        }
    }
}
