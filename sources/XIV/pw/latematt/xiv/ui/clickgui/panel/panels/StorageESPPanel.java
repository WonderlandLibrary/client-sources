package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class StorageESPPanel extends Panel {
    public StorageESPPanel(float x, float y) {
        super("StorageESP", new ArrayList<>(), x, y, 20, 10);

        addValueElements("storage_esp_");
    }
}
