package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class HUBPanel extends Panel {
    public HUBPanel(float x, float y) {
        super("HUB", new ArrayList<>(), x, y, 20, 10, true);

        addPanelElements();
    }
}
