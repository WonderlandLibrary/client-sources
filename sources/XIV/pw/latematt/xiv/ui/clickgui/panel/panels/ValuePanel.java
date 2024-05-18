package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Matthew
 */
public class ValuePanel extends Panel {
    public ValuePanel(float x, float y) {
        super("Value", new ArrayList<>(), x, y, 20, 10);
        this.setOpen(false);

        addSliders();
    }
}
