package none.module.modules.render;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import none.event.Event;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;
import none.valuesystem.NumberValue;

public class ClientColor extends Module{

	public ClientColor() {
		super("ClientColor", "ClientColor", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	public static NumberValue<Integer> red = new NumberValue<Integer>("Red", 0, 0, 255);
	public static NumberValue<Integer> green = new NumberValue<Integer>("Green", 170, 0, 255);
	public static NumberValue<Integer> blue = new NumberValue<Integer>("Blue", 255, 0, 255);
	public static BooleanValue rainbow = new BooleanValue("Rainbow", true);
	
	public static int getColor() {
        int color = 0;
        color |= 255 << 24;
        color |= red.getObject() << 16;
        color |= green.getObject() << 8;
        color |= blue.getObject();
        return color;
    }
    
    public static int rainbow(int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 20.0);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0f), 0.8f, 0.7f).getRGB();
    }
	
	@Override
	protected void onEnable() {
		super.onEnable();
		toggle();
	}

	@Override
	public void onEvent(Event event) {
		
	}
}
