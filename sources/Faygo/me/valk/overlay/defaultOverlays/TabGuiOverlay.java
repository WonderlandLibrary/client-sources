package me.valk.overlay.defaultOverlays;

import me.valk.Vital;
import me.valk.overlay.VitalOverlay;

public class TabGuiOverlay extends VitalOverlay {
	
	public TabGuiOverlay(){
		super("TabGui");
	}
	
	@Override
	public void render() {
		Vital.getVital().getTabGui().onRender();
	}
	
}
