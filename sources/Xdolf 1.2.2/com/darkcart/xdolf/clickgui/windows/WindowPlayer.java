package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.clickgui.elements.XdolfWindow;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.Category;

public class WindowPlayer extends XdolfWindow {
	
	public WindowPlayer() {
		super("Player", 2, 47);
		this.loadButtonsFromCategory(Category.PLAYER);
	}
}

