package pw.latematt.xiv.ui.clickgui.panel.panels;

import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.ui.clickgui.panel.Panel;

import java.util.ArrayList;

/**
 * @author Rederpz ;)
 */
public class ModulePanel extends Panel {
    public ModulePanel(ModType type, float x, float y) {
        super(type.getName(), new ArrayList<>(), x, y, 20, 10);

        addModuleElements(type);
    }
}
