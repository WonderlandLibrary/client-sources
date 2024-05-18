package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.clickgui.elements.XuluBWindow;
import com.darkcart.xdolf.util.Category;

public class WindowRender extends XuluBWindow {
	public WindowRender() {
		super("Render", 2, 62);
		this.loadButtonsFromCategory(Category.RENDER);
	}
}