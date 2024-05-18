package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XuluBWindow;
import com.darkcart.xdolf.util.Category;

public class WindowMovement extends XuluBWindow
{
	public WindowMovement() {
		super("Movement", 2, 87);
		this.loadButtonsFromCategory(Category.MOVEMENT);
	}
}
