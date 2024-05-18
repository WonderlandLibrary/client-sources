package me.valk.manager.managers;

import me.valk.manager.Manager;
import me.valk.overlay.VitalOverlay;

public class OverlayManager extends Manager<VitalOverlay>{

	public void removeOverlay(String name){
		for(int i = 0; i < getContents().size(); i++){
			VitalOverlay overlay = getContents().get(i);
			if(overlay.getName() == name){
				this.removeContent(overlay);
				break;
			}
		}
	}
	
}
