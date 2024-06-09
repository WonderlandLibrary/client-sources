package me.valk.overlay.tabGui;

import java.util.ArrayList;
import java.util.List;

import me.valk.event.EventListener;
import me.valk.event.EventSystem;
import me.valk.event.events.other.EventKeyPress;
import me.valk.event.events.other.EventTick;
import me.valk.module.ModType;
import me.valk.overlay.tabGui.parts.TabPanel;
import me.valk.overlay.tabGui.parts.TabTypePart;
import me.valk.overlay.tabGui.theme.TabTheme;

public class TabGui {

	private TabPanel currentPanel;
	private List<TabPanel> panels = new ArrayList<TabPanel>();
	private TabTheme theme;

	public TabGui(TabTheme theme){
		EventSystem.register(this);
		this.theme = theme;

		TabPanel main = new TabPanel(this);

		for(ModType type : ModType.values()){
			main.addElement(new TabTypePart(type, main));
		}

		main.setVisible(true);
		addPanel(main);
		main.setIndex(0);
	}

	public int getHighestIndex(){
		int index = 0;
		
		for(TabPanel panel : panels){
			if(panel.getIndex() > index) index = panel.getIndex();
		}
		
		return index;
	}
	
	public List<TabPanel> getPanels(){
		return this.panels;
	}

	public void setTabTheme(TabTheme theme){
		this.theme = theme;
	}

	public TabTheme getTabTheme(){
		return this.theme;
	}

	public void addPanel(TabPanel panel){
		if(this.currentPanel == null) this.currentPanel = panel;
		this.panels.add(panel);
	}

	public void removePanel(TabPanel panel){
		this.panels.remove(panel);
	}

	@EventListener
	public void onUpdate(EventTick event){
		findCurrentPanel();
		for(TabPanel panel : panels) panel.onUpdate();
	}

	@EventListener
	public void onKeyPress(EventKeyPress event){
		findCurrentPanel();
		this.currentPanel.onKeyPress(event.getKey());
	}

	public void findCurrentPanel(){
		TabPanel current = null;

		for(TabPanel panel : getPanels()){
			if(current == null) current = panel;
			
			if(panel.getIndex() > current.getIndex()){
				current = panel;
			}
		}
		
		this.currentPanel = current;
	}

	public void onRender(){
		findCurrentPanel();
		for(TabPanel panel : panels) panel.renderPanel();
	}

}
