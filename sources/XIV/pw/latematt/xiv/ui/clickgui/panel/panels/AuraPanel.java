package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class AuraPanel extends Panel {
    public AuraPanel(float x, float y) {
        super("Aura", new ArrayList<>(), x, y, 20, 10);

        addValueElements("killaura_");
    }
}
