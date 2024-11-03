package net.silentclient.client.gui.theme.checkbox;

import java.awt.*;

public class DefaultCheckboxTheme implements ICheckboxTheme {
    @Override
    public Color getColor() {
        return new Color(214, 213, 210);
    }

    @Override
    public Color getSelectedColor() {
        return new Color(165, 227, 177);
    }
}
