package net.silentclient.client.gui.theme.button;

import java.awt.*;

public interface IButtonTheme {
    Color getBorderColor();
    Color getBackgroundColor();
    Color getTextColor();
    Color getHoveredBackgroundColor(int opacity);
}
