package mods.togglesprint.me.imfr0zen.guiapi.listeners;

import java.util.ArrayList;

import mods.togglesprint.me.imfr0zen.guiapi.components.GuiComponent;

/**
 * 
 * @author ImFr0zen
 *
 */
public abstract class ExtendListener {

	private final ArrayList<GuiComponent> components = new ArrayList<GuiComponent>();
	
	public ExtendListener() {}
	
	/**
	 * Adds component to the List.
	 * @param component
	 */
	protected void add(GuiComponent component) {
		components.add(component);
	}
	
	/**
	 * Clears all components from the List.
	 */
	public void clearComponents() {
		components.clear();
	}
	
	/**
	 * @return All Components.
	 */
	public ArrayList<GuiComponent> getComponents() {
		return components;
	}
	
	/**
	 * Add your components to the List.
	 */
	public abstract void addComponents();

}
