package me.swezedcode.client.gui.cgui.parts;

import java.util.ArrayList;

import me.swezedcode.client.gui.cgui.component.Component;

public abstract class Parts extends Component
{
	protected ArrayList<Component> components;
    protected boolean open;
    
    public Parts() {
        this.components = new ArrayList<Component>();
    }
    
    public void addComponent(final Component component) {
        this.components.add(component);
    }
    
    public ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public boolean isOpen() {
        return this.open;
    }

	public void setOpen(boolean open) {
		this.open = open;
	}
}
