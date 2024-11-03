package net.silentclient.client.gui.theme;

import net.silentclient.client.Client;

import java.awt.*;

public class Theme {
    public static Color backgroundColor() {
        if(Client.getInstance().getGlobalSettings() != null && Client.getInstance().getGlobalSettings().isLite()) {
            return new Color(0, 0, 0, 180);
        }
        return new Color(20, 20, 20);
    }

    public static Color borderColor() {
        return new Color(214, 213, 210);
    }
}
