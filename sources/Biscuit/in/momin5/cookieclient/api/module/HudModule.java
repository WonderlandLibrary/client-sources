package in.momin5.cookieclient.api.module;

import com.lukflug.panelstudio.FixedComponent;
import com.lukflug.panelstudio.theme.Theme;
import in.momin5.cookieclient.CookieClient;

import java.awt.*;

public abstract class HudModule extends Module {

    protected FixedComponent component;
    protected Point position;

    public HudModule (String title, Point defaultPos, Category category) {
        super(title, category);
        position = defaultPos;
    }

    public abstract void populate (Theme theme);

    public FixedComponent getComponent() {
        return component;
    }

    public void resetPosition() {
        component.setPosition(CookieClient.clickGUI.guiInterface,position);
    }
}
