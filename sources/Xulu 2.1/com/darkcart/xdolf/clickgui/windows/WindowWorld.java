package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XuluBWindow;
import com.darkcart.xdolf.util.Category;

public class WindowWorld extends XuluBWindow
{
	public WindowWorld() {
		super("World", 2, 77);
		this.loadButtonsFromCategory(Category.WORLD);
	}
}
