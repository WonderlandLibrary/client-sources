package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import none.Client;
import none.event.Event;
import none.module.Category;
import none.module.Module;
import none.valuesystem.StringValue;

public class MusicPlayer extends Module{
	public static StringValue path = new StringValue("Path-Location", "No Path");
	public MusicPlayer() {
		super("MusicPlayer", "MusicPlayer", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		mc.displayGuiScreen(Client.instance.musicScreen);
		super.onEnable();
		setState(false);
	}
	
	@Override
	public void onEvent(Event event) {
		
	}

}
