// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.tabgui;

import net.andrewsnetwork.icarus.event.events.KeyPressed;
import net.andrewsnetwork.icarus.event.Event;
import java.util.Objects;
import net.andrewsnetwork.icarus.utilities.RenderHelper;
import java.util.Iterator;
import net.andrewsnetwork.icarus.tabgui.tab.GuiItem;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.module.Module;
import java.awt.Font;
import net.andrewsnetwork.icarus.tabgui.tab.GuiTab;
import java.util.ArrayList;
import net.andrewsnetwork.icarus.utilities.UnicodeFontRenderer;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.event.Listener;

public class GuiTabHandler implements Listener
{
    private final Minecraft mc;
    private final TimeHelper time;
    private UnicodeFontRenderer fontRenderer;
    private int colourBody;
    private int colourBox;
    private int guiHeight;
    private int guiWidth;
    private boolean mainMenu;
    private int selectedItem;
    private int selectedTab;
    private int tabHeight;
    private final ArrayList<GuiTab> tabs;
    private int transition;
    
    public GuiTabHandler() {
        this.mc = Minecraft.getMinecraft();
        this.time = new TimeHelper();
        this.fontRenderer = new UnicodeFontRenderer(new Font("Impact", 0, 18));
        this.colourBody = -1795162112;
        this.colourBox = -862535474;
        this.guiHeight = 0;
        this.guiWidth = 74;
        this.mainMenu = true;
        this.selectedItem = 0;
        this.selectedTab = 0;
        this.tabHeight = 12;
        this.tabs = new ArrayList<GuiTab>();
        this.transition = 0;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            final Module.Category category = values[i];
            final GuiTab tab = new GuiTab(this, category.getName());
            for (final Module module : Icarus.getModuleManager().getModules()) {
                if (module.getCategory() == category) {
                    tab.getMods().add(new GuiItem(module));
                }
            }
            this.tabs.add(tab);
        }
        this.guiHeight = this.tabs.size() * this.tabHeight;
        Icarus.getEventManager().addListener(this);
    }
    
    public void drawGui(final int x, final int y) {
        RenderHelper.drawBorderedRect(x, y - 40, x + this.guiWidth - 2, y + this.guiHeight, 1.0f, -16777216, this.colourBody);
        for (int i = 0; i < this.tabs.size(); ++i) {
            final int transitionTop = this.mainMenu ? (this.transition + ((this.selectedTab == 0 && this.transition < 0) ? (-this.transition) : 0)) : 0;
            final int transitionBottom = this.mainMenu ? (this.transition + ((this.selectedTab == this.tabs.size() - 1 && this.transition > 0) ? (-this.transition) : 0)) : 0;
            if (Objects.equals(this.selectedTab, i)) {
                RenderHelper.drawBorderedRect(x, i * 12 + y + transitionTop, x + this.guiWidth - 2, i + (y + 12 + i * 11) + transitionBottom, 1.0f, 0, this.colourBox);
            }
        }
        int yOff = y + 2;
        for (int j = 0; j < this.tabs.size(); ++j) {
            final GuiTab tab = this.tabs.get(j);
            this.fontRenderer.drawStringWithShadow(tab.getTabName(), x + 2, yOff - 2, -1);
            if (Objects.equals(this.selectedTab, j) && !this.mainMenu) {
                tab.drawTabMenu(this.mc, x - 2, yOff - 2);
            }
            yOff += this.tabHeight;
        }
        if (this.transition > 0) {
            --this.transition;
        }
        else if (this.transition < 0) {
            ++this.transition;
        }
    }
    
    public int getColourBody() {
        return this.colourBody;
    }
    
    public int getColourBox() {
        return this.colourBox;
    }
    
    public String getColourHightlight() {
        return "§f";
    }
    
    public String getColourNormal() {
        return "§7";
    }
    
    public int getSelectedItem() {
        return this.selectedItem;
    }
    
    public int getTabHeight() {
        return this.tabHeight;
    }
    
    public int getTransition() {
        return this.transition;
    }
    
    public UnicodeFontRenderer getFontRenderer() {
        return this.fontRenderer;
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof KeyPressed) {
            final KeyPressed event = (KeyPressed)e;
            switch (event.getKey()) {
                case 200: {
                    if (this.mainMenu) {
                        --this.selectedTab;
                        if (this.selectedTab < 0) {
                            this.selectedTab = this.tabs.size() - 1;
                        }
                        this.transition = 11;
                        break;
                    }
                    --this.selectedItem;
                    if (this.selectedItem < 0) {
                        this.selectedItem = this.tabs.get(this.selectedTab).getMods().size() - 1;
                    }
                    if (this.tabs.get(this.selectedTab).getMods().size() > 1) {
                        this.transition = 11;
                        break;
                    }
                    break;
                }
                case 208: {
                    if (this.mainMenu) {
                        ++this.selectedTab;
                        if (this.selectedTab > this.tabs.size() - 1) {
                            this.selectedTab = 0;
                        }
                        this.transition = -11;
                        break;
                    }
                    ++this.selectedItem;
                    if (this.selectedItem > this.tabs.get(this.selectedTab).getMods().size() - 1) {
                        this.selectedItem = 0;
                    }
                    if (this.tabs.get(this.selectedTab).getMods().size() > 1) {
                        this.transition = -11;
                        break;
                    }
                    break;
                }
                case 205: {
                    if (!this.mainMenu) {
                        this.mainMenu = true;
                        break;
                    }
                    break;
                }
                case 203: {
                    if (this.mainMenu) {
                        this.mainMenu = false;
                        this.selectedItem = 0;
                        break;
                    }
                    if (!this.mainMenu) {
                        this.tabs.get(this.selectedTab).getMods().get(this.selectedItem).getModule().toggle();
                        break;
                    }
                    break;
                }
                case 28: {
                    if (!this.mainMenu) {
                        this.tabs.get(this.selectedTab).getMods().get(this.selectedItem).getModule().toggle();
                        break;
                    }
                    break;
                }
            }
        }
    }
}
