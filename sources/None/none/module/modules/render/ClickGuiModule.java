package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import none.Client;
import none.event.Event;
import none.module.Category;
import none.module.Module;
import none.noneClickGui.NoneClickGui;
import none.valuesystem.ModeValue;

public class ClickGuiModule extends Module{
	
	private String[] modes = {"None", "None2"};
	public ModeValue GuiMode = new ModeValue("Style", "None2", modes);
	
	public ClickGuiModule() {
		super("ClickGui", "ClickGui", Category.RENDER, Keyboard.KEY_RSHIFT);
	}

	@Override
	protected void onEnable() {
		mc.displayGuiScreen(Client.instance.clickgui);
        setEnabled(false);
	}

	@Override
	public void onEvent(Event event) {
		
	}
}
