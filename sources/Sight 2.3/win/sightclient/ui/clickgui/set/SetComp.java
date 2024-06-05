package win.sightclient.ui.clickgui.set;

import win.sightclient.module.settings.Setting;
import win.sightclient.ui.clickgui.Button;

public class SetComp {

	protected Button parent;
	private Setting setting;
	
	public SetComp(Setting s, Button parent) {
		this.parent = parent;
		this.setting = s;
	}

	public int drawScreen(int mouseX, int mouseY, int x, int y) {
		return 0;
	}
	
	public void mouseClicked(int x, int y, int button) {}
	
	public void mouseReleased(int mouseX, int mouseY, int state) {}
	
	public void keyTyped(char typedChar, int keyCode) {}
	
	public Setting getSetting() {
		return this.setting;
	}
}
