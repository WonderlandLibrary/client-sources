package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class ESPPanel extends Panel {
    public ESPPanel(float x, float y) {
        super("ESP", new ArrayList<>(), x, y, 20, 10);

        addValueElements("esp_");
    }
}
