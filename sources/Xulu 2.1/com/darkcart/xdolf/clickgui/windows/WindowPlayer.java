package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XuluBWindow;
import com.darkcart.xdolf.util.Category;

public class WindowPlayer extends XuluBWindow {
	
	public WindowPlayer() {
		super("Player", 2, 47);
		this.loadButtonsFromCategory(Category.PLAYER);
	}
}

