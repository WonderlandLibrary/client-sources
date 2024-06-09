package ca.commencal.ware.gui.click.elements;

import ca.commencal.ware.gui.click.base.Component;
import ca.commencal.ware.gui.click.base.ComponentType;
import ca.commencal.ware.gui.click.listener.CheckButtonClickListener;
import ca.commencal.ware.value.Mode;
import ca.commencal.ware.value.ModeValue;

import java.util.ArrayList;

public class CheckButton extends Component {

    public ArrayList<CheckButtonClickListener> listeners = new ArrayList<CheckButtonClickListener>();

    private boolean enabled = false;
    private ModeValue modeValue = null;

    public CheckButton(int xPos, int yPos, int width, int height, Component component, String text, boolean enabled, ModeValue modeValue) {

        super(xPos, yPos, width, height, ComponentType.CHECK_BUTTON, component, text);
        this.enabled = enabled;
        this.modeValue = modeValue;
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
    	if(modeValue != null) {
    		for(Mode mode : modeValue.getModes()) {
    			mode.setToggled(false);
    		}
    		this.enabled = true;
    	} 
    	else {
    		this.enabled = !this.enabled;
    	}
        for (CheckButtonClickListener listener : listeners) {
            listener.onCheckButtonClick(this);
        }
    }

    public ArrayList<CheckButtonClickListener> getListeners() {

        return listeners;
    }

    public void addListeners(CheckButtonClickListener listener) {

        listeners.add(listener);
    }

    public boolean isEnabled() {

        return enabled;
    }
    
    public ModeValue getModeValue() {
		return modeValue;
	}

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }
}
