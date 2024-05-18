package me.valk.overlay.tabGui.theme;

import java.util.ArrayList;
import java.util.List;

import me.valk.overlay.tabGui.TabObject;
import me.valk.overlay.tabGui.TabPartRenderer;
import me.valk.utils.render.VitalFontRenderer;

public class TabTheme {

	private List<TabPartRenderer> partRenderers = new ArrayList<TabPartRenderer>();
	private VitalFontRenderer fontRenderer;
	
	public TabTheme(VitalFontRenderer fontRenderer){
		this.fontRenderer = fontRenderer;
	}
	
	public void addPartRenderer(TabPartRenderer partRenderer){
		this.partRenderers.add(partRenderer);
	}
	
	public VitalFontRenderer getFontRenderer(){
		return this.fontRenderer;
	}
	
	public void renderElement(TabObject object){
		for(TabPartRenderer renderer : partRenderers){
			if(object.getClass().getCanonicalName().equals(renderer.getType().getCanonicalName())){
				renderer.render(object);
			}
		}
	}
	
}
