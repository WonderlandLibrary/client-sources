package us.loki.legit.modules.impl.Other;

import org.lwjgl.input.Keyboard;

import us.loki.legit.modules.*;

public class ChatCommands extends Module{
	
	public ChatCommands() {
		super("ChatCommands", "ChatCommands", Keyboard.KEY_NONE, Category.OTHER);
	}

}