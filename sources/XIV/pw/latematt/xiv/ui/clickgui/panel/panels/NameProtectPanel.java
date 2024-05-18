package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class NameProtectPanel extends Panel {
    public NameProtectPanel(float x, float y) {
        super("NameProtect", new ArrayList<>(), x, y, 20, 10);

        addValueElements("nameprotect_");
    }
}
