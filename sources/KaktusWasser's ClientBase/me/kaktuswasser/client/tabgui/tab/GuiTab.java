// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.tabgui.tab;

import java.util.Iterator;

import me.kaktuswasser.client.tabgui.GuiTabHandler;
import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.utilities.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;
import java.awt.Font;
import java.util.ArrayList;

public class GuiTab
{
    private GuiTabHandler gui;
    private UnicodeFontRenderer fontRenderer;
    private ArrayList<GuiItem> mods;
    private int menuHeight;
    public static int menuWidth;
    private String tabName;
    
    public GuiTab(GuiTabHandler gui, String tabName) {
        fontRenderer = new UnicodeFontRenderer(new Font("Impact", 0, 18));
        mods = new ArrayList<GuiItem>();
        menuHeight = 0;
        menuWidth = 0;
        this.gui = gui;
        this.tabName = tabName;
    }
    
    public void drawTabMenu(Minecraft mc, int x, int y) {
        countMenuSize(mc);
        int boxY = y;
        	RenderHelper.drawBorderedRect(x - menuWidth, y, x, y + menuHeight, 2.5f, -16777216, gui.getColourBody()); 	
        for (int i = 0; i < mods.size(); ++i) {
            if (gui.getSelectedItem() == i) {
                int transitionTop = gui.getTransition() + ((gui.getSelectedItem() == 0 && gui.getTransition() < 0) ? (-gui.getTransition()) : 0);
                int transitionBottom = gui.getTransition() + ((gui.getSelectedItem() == mods.size() - 1 && gui.getTransition() > 0) ? (-gui.getTransition()) : 0);
                	RenderHelper.drawBorderedRect(x - this.menuWidth, boxY + transitionTop, x, boxY + 12 + transitionBottom, 1.0f, 0, 0x9071f442);	
            }
            boxY += 12;
        }
        for (int i = 0; i < mods.size(); ++i) {
        		gui.getFontRenderer().drawStringWithShadow(String.valueOf(mods.get(i).getModule().isEnabled() ? gui.getColourHightlight() : gui.getColourNormal()) + mods.get(i).getName(), x - menuWidth + 1, y + gui.getTabHeight() * i, -1);
        }
    }
    
    private void countMenuSize(Minecraft mc) {
        int maxWidth = 0;
        for (GuiItem mod : mods) {
            if (fontRenderer.getRealStringWidth(mod.getName()) > maxWidth) {
            		maxWidth = fontRenderer.getRealStringWidth(mod.getName()) + 6;
            }
        }
        this.menuWidth = maxWidth;
        this.menuHeight = mods.size() * gui.getTabHeight();
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
