package pw.latematt.xiv.ui.tabgui.tab;

import pw.latematt.xiv.mod.Mod;

/**
 * @author Jack
 */

public class GuiItem {
    private final Mod mod;
    private String name;

    public GuiItem(final Mod mod) {
        this.mod = mod;
        this.name = mod.getName();
    }

    public Mod getMod() {
        return mod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
