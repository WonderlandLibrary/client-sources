package com.darkcart.xdolf.clickgui.windows;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.clickgui.elements.XdolfWindow;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.Category;

public class WindowRender extends XdolfWindow {
	public WindowRender() {
		super("Render", 2, 62);
		this.loadButtonsFromCategory(Category.RENDER);
	}
}