package net.silentclient.client.gui.theme.switches;

import java.awt.*;

public class DefaultSwitchTheme implements ISwitchSchema {
    @Override
    public Color getSelectedBackgroundColor() {
        return new Color(165, 227, 177);
    }

    @Override
    public Color getDisabledBackgroundColor() {
        return new Color(229, 62, 62);
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(255, 255, 255, 61);
    }

    @Override
    public Color getCircleColor() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getBorderColor() {
        return new Color(214, 213, 210);
    }
}
