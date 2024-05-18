package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class WaypointsPanel extends Panel {
    public WaypointsPanel(float x, float y) {
        super("Waypoints", new ArrayList<>(), x, y, 20, 10);

        addValueElements("waypoints_");
    }
}
