package net.augustus.modules.render;

import java.awt.Color;

import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.settings.BooleanValue;

public class Chat extends Module{

	   public BooleanValue ShadowText;
	   public BooleanValue background;
	
	public Chat() {
		super("Chat", Color.GRAY, Categorys.RENDER);
		ShadowText = new BooleanValue(768945789, "ShadowText", this, true);
		background = new BooleanValue(124589345, "Background", this, true);
	}
	
}
