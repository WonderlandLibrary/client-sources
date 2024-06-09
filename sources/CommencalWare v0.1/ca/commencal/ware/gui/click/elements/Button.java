package ca.commencal.ware.gui.click.elements;

import ca.commencal.ware.gui.click.base.Component;
import ca.commencal.ware.gui.click.base.ComponentType;
import ca.commencal.ware.gui.click.listener.ComponentClickListener;
import ca.commencal.ware.module.Module;

import java.util.ArrayList;

public class Button extends Component {

    public ArrayList<ComponentClickListener> listeners = new ArrayList<ComponentClickListener>();

    private Module mod;

    private boolean enabled = false;

    public Button(int xPos, int yPos, int width, int height, Component component, String text) {

        super(xPos, yPos, width, height, ComponentType.BUTTON, component, text);
    }

    public Button(int xPos, int yPos, int width, int height, Component component, String text, Module mod) {

        super(xPos, yPos, width, height, ComponentType.BUTTON, component, text);
        this.mod = mod;
    }

    public void addListeners(ComponentClickListener listener) {

        listeners.add(listener);
    }

    public void onMousePress(int x, int y, int button) {

        if (button != 0) {
            return;
        }

        this.enabled = !this.enabled;

        for (ComponentClickListener listener : listeners) {
            listener.onComponenetClick(this, button);
        }
    }

    public boolean isEnabled() {

        return enabled;
    }

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }

    public ArrayList<ComponentClickListener> getListeners() {

        return listeners;
    }

    public Module getMod() {

        return mod;
    }
}
