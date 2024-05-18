package pw.latematt.xiv.ui.tabgui.tab;

import net.minecraft.client.Minecraft;
import pw.latematt.xiv.ui.tabgui.GuiTabHandler;
import pw.latematt.xiv.utils.RenderUtils;

import java.util.ArrayList;

/**
 * @author Jack
 */

public class GuiTab {
    private final GuiTabHandler gui;
    private final ArrayList<GuiItem> mods = new ArrayList<>();
    private int menuHeight = 0;
    private int menuWidth = 0;
    private String tabName;

    public GuiTab(final GuiTabHandler gui, final String tabName) {
        this.gui = gui;
        this.tabName = tabName;
    }

    public void drawTabMenu(Minecraft mc, int x, int y) {
        this.countMenuSize(mc);
        int boxY = y;

        RenderUtils.drawBorderedRect(x, y, x + this.menuWidth, y + this.menuHeight, 0x80000000, this.gui.getColourBody());
        for (int i = 0; i < this.mods.size(); i++) {
            if (this.gui.getSelectedItem() == i) {
                int transitionTop = this.gui.getTransition() + (this.gui.getSelectedItem() == 0 && this.gui.getTransition() < 0 ? -this.gui.getTransition() : 0);
                int transitionBottom = this.gui.getTransition() + (this.gui.getSelectedItem() == this.mods.size() - 1 && this.gui.getTransition() > 0 ? -this.gui.getTransition() : 0);

                RenderUtils.drawBorderedRect(x, boxY + transitionTop, x + this.menuWidth, boxY + 12 + transitionBottom, 0x80000000, this.gui.getColourBox());
            }

            mc.fontRendererObj.drawStringWithShadow((this.mods.get(i).getMod().isEnabled() ? this.gui.getColourHightlight() : this.gui.getColourNormal()) + this.mods.get(i).getName(), x + 2, y + this.gui.getTabHeight() * i + 2, 0xFFFFFFFF);

            boxY += 12;
        }
    }

    private void countMenuSize(Minecraft mc) {
        int maxWidth = 0;
        for (GuiItem mod : this.mods) {
            if (mc.fontRendererObj.getStringWidth(mod.getName()) > maxWidth) {
                maxWidth = 2 + mc.fontRendererObj.getStringWidth(mod.getName()) + 2;
            }
//            System.out.println(mod.getName() + " ee " + (2 + mc.fontRendererObj.getStringWidth(mod.getName() + "r") + 2));
        }

        this.menuWidth = maxWidth;

        this.menuHeight = this.mods.size() * this.gui.getTabHeight();
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public ArrayList<GuiItem> getMods() {
        return mods;
    }
}
