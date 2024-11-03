package net.silentclient.client.gui.theme.button;

import java.awt.*;

public class SelectedButtonTheme implements IButtonTheme {
    @Override
    public Color getBorderColor() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getBackgroundColor() {
        return new Color(255, 255, 255);
    }

    @Override
    public Color getTextColor() {
        return new Color(0, 0, 0);
    }

    @Override
    public Color getHoveredBackgroundColor(int opacity) {
        return new Color(255, 255, 255, opacity);
    }
}
