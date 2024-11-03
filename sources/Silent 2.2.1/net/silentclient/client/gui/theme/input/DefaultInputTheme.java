package net.silentclient.client.gui.theme.input;

import java.awt.*;

public class DefaultInputTheme implements IInputTheme {
    @Override
    public Color getBorderColor() {
        return new Color(214, 213, 210);
    }

    @Override
    public Color getHoveredBorderColor() {
        return new Color(230, 230, 230);
    }

    @Override
    public Color getFocusedBorderColor() {
        return new Color(255, 255, 255);
    }
}
