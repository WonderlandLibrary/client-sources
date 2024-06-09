// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.tabgui.tab;

import java.util.Iterator;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import net.minecraft.client.Minecraft;
import java.awt.Font;
import java.util.ArrayList;
import net.andrewsnetwork.icarus.utilities.UnicodeFontRenderer;
import net.andrewsnetwork.icarus.tabgui.GuiTabHandler;

public class GuiTab
{
    private final GuiTabHandler gui;
    private UnicodeFontRenderer fontRenderer;
    private final ArrayList<GuiItem> mods;
    private int menuHeight;
    private int menuWidth;
    private String tabName;
    
    public GuiTab(final GuiTabHandler gui, final String tabName) {
        this.fontRenderer = new UnicodeFontRenderer(new Font("Impact", 0, 18));
        this.mods = new ArrayList<GuiItem>();
        this.menuHeight = 0;
        this.menuWidth = 0;
        this.gui = gui;
        this.tabName = tabName;
    }
    
    public void drawTabMenu(final Minecraft mc, final int x, final int y) {
        this.countMenuSize(mc);
        int boxY = y;
        RenderHelper.drawBorderedRect(x - this.menuWidth, y, x, y + this.menuHeight, 1.0f, -16777216, this.gui.getColourBody());
        for (int i = 0; i < this.mods.size(); ++i) {
            if (this.gui.getSelectedItem() == i) {
                final int transitionTop = this.gui.getTransition() + ((this.gui.getSelectedItem() == 0 && this.gui.getTransition() < 0) ? (-this.gui.getTransition()) : 0);
                final int transitionBottom = this.gui.getTransition() + ((this.gui.getSelectedItem() == this.mods.size() - 1 && this.gui.getTransition() > 0) ? (-this.gui.getTransition()) : 0);
                RenderHelper.drawBorderedRect(x - this.menuWidth, boxY + transitionTop, x, boxY + 12 + transitionBottom, 1.0f, 0, this.gui.getColourBox());
            }
            boxY += 12;
        }
        for (int i = 0; i < this.mods.size(); ++i) {
            this.gui.getFontRenderer().drawStringWithShadow(String.valueOf(this.mods.get(i).getModule().isEnabled() ? this.gui.getColourHightlight() : this.gui.getColourNormal()) + this.mods.get(i).getName(), x - this.menuWidth + 2, y + this.gui.getTabHeight() * i, -1);
        }
    }
    
    private void countMenuSize(final Minecraft mc) {
        int maxWidth = 0;
        for (final GuiItem mod : this.mods) {
            if (this.fontRenderer.getRealStringWidth(mod.getName()) > maxWidth) {
                maxWidth = this.fontRenderer.getRealStringWidth(mod.getName()) + 6;
            }
        }
        this.menuWidth = maxWidth;
        this.menuHeight = this.mods.size() * this.gui.getTabHeight();
    }
    
    public String getTabName() {
        return this.tabName;
    }
    
    public void setTabName(final String tabName) {
        this.tabName = tabName;
    }
    
    public ArrayList<GuiItem> getMods() {
        return this.mods;
    }
}
