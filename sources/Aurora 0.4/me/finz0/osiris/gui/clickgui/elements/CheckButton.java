package me.finz0.osiris.gui.clickgui.elements;

import de.Hero.settings.Setting;
import de.Hero.settings.SettingsManager;
import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.listener.CheckButtonClickListener;

import java.util.ArrayList;

public class CheckButton extends Component {

    public ArrayList<CheckButtonClickListener> listeners = new ArrayList<CheckButtonClickListener>();

    private boolean enabled = false;
    private Setting modeSetting = null;

    public CheckButton(int xPos, int yPos, int width, int height, Component component, String text, boolean enabled, Setting modeSetting) {
        super(xPos, yPos, width, height, ComponentType.CHECK_BUTTON, component, text);
        this.enabled = enabled;
        this.modeSetting = modeSetting;
    }

    @Override
    public void onMousePress(int x, int y, int buttonID) {
        if (modeSetting != null) {

            String value = this.getText();
            AuroraMod.getInstance().settingsManager.getSettingByID(modeSetting.getId()).setValString(value);
            System.out.println(AuroraMod.getInstance().settingsManager.getSettingByID(modeSetting.getId()).getValString());


            this.enabled = true;
        } else {
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

    public Setting getModeSetting() {
        return modeSetting;
    }

    public void setEnabled(boolean enabled) {

        this.enabled = enabled;
    }
}
