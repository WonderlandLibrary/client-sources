package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XdolfWindow;
import com.darkcart.xdolf.util.Category;

public class WindowWorld extends XdolfWindow
{
	public WindowWorld() {
		super("World", 2, 77);
		this.loadButtonsFromCategory(Category.WORLD);
	}
}
