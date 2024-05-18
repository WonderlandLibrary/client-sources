package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XuluBWindow;
import com.darkcart.xdolf.util.Category;

public class WindowAura extends XuluBWindow
{
	public WindowAura() {
		super("Combat", 2, 32);
		this.loadButtonsFromCategory(Category.COMBAT);
	}
}
