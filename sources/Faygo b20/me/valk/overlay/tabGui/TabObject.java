package me.valk.overlay.tabGui;

import me.valk.overlay.tabGui.parts.TabPanel;

public class TabObject {

	private TabPanel parent;
	
	public TabObject(TabPanel parent) {
		this.parent = parent;
	}

	public void onKeyPress(int key){}

	public TabPanel getParent(){
		return this.parent;
	}
}
