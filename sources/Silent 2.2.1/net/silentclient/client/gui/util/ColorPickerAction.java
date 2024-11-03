package net.silentclient.client.gui.util;

import java.awt.*;

public interface ColorPickerAction {
    void onChange(Color color, boolean chroma, int opacity);
    default void onClose(Color color, boolean chroma, int opacity) {

    }
}
