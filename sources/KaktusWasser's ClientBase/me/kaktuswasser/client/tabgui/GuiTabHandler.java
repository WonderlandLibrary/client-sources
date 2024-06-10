// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.tabgui;

import java.util.Objects;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.Listener;
import me.kaktuswasser.client.event.events.KeyPressed;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.module.modules.GUI;
import me.kaktuswasser.client.tabgui.tab.GuiItem;
import me.kaktuswasser.client.tabgui.tab.GuiTab;
import me.kaktuswasser.client.utilities.RenderHelper;
import me.kaktuswasser.client.utilities.TimeHelper;
import me.kaktuswasser.client.utilities.UnicodeFontRenderer;

import java.util.Iterator;
import java.awt.Font;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;

public class GuiTabHandler implements Listener {
	
	public static boolean right = false;
	
    private Minecraft mc;
    private TimeHelper time;
    private UnicodeFontRenderer fontRenderer;
    public static int colourBody;
    private int colourBox;
    private int guiHeight;
    public static int guiWidth;
    private boolean mainMenu;
    private int selectedItem;
    private int selectedTab;
    private int tabHeight;
    private ArrayList<GuiTab> tabs;
    private int transition;
    
    public GuiTabHandler() {
        mc = Minecraft.getMinecraft();
        time = new TimeHelper();
        fontRenderer = new UnicodeFontRenderer(new Font("Impact", 0, 18));
        colourBody = -1795162112;
        colourBox = -862535474;
        guiHeight = 0;
        guiWidth = 74;
        mainMenu = true;
        selectedItem = 0;
        selectedTab = 0;
        tabHeight = 12;
        tabs = new ArrayList<GuiTab>();
        transition = 0;
        Module.Category[] values;
        for (int length = (values = Module.Category.values()).length, i = 0; i < length; ++i) {
            Module.Category category = values[i];
            GuiTab tab = new GuiTab(this, category.getName());
            for (Module module : Client.getModuleManager().getModules()) {
                if (module.getCategory() == category) {
                    tab.getMods().add(new GuiItem(module));
                }
            }
            tabs.add(tab);
        }
        guiHeight = tabs.size() * tabHeight;
        Client.getEventManager().addListener(this);
    }
    
    public void drawTabGui(int x, int y) {
        RenderHelper.drawBorderedRect(x, y, x + guiWidth - 2, y + guiHeight, 2.5f, -16777216, colourBody);
        for (int i = 0; i < tabs.size(); ++i) {
            int transitionTop = mainMenu ? (transition + ((selectedTab == 0 && transition < 0) ? (-transition) : 0)) : 0;
            int transitionBottom = mainMenu ? (transition + ((selectedTab == tabs.size() - 1 && transition > 0) ? (-transition) : 0)) : 0;
            if (Objects.equals(selectedTab, i)) {
	            RenderHelper.drawBorderedRect(x, i * 12 + y + transitionTop, x + guiWidth - 2, i + (y + 12 + i * 11) + transitionBottom, 3.0f, 0, 0x9071f442);
                RenderHelper.drawBorderedRect(x, i * 12 + y + transitionTop, x + guiWidth - 72, i + (y + 12 + i * 11) + transitionBottom, 3.0f, 0, 0x9971f442);
            }
        }
        int yOff = y + 2;
        for (int j = 0; j < tabs.size(); ++j) {
            GuiTab tab = tabs.get(j);
            fontRenderer.drawStringWithShadow(tab.getTabName(), x + 2, yOff - 2, -1);
            if (Objects.equals(selectedTab, j) && !mainMenu) {
            	if(right == true) {
            		tab.drawTabMenu(mc, x - 2, yOff - 2);      		
            	}else if(right == false) {
            		tab.drawTabMenu(mc, guiHeight + GuiTab.menuWidth - 8, yOff - 2); 
            	}
            	
            }
            yOff += tabHeight;
        }
        if (transition > 0) {
            --transition;
        }
        else if (transition < 0) {
            ++transition;
        }
    }
    
    public int getColourBody() {
        return colourBody;
    }
    
    public int getColourBox() {
        return colourBox;
    }
    
    public String getColourHightlight() {
        return "§a";
    }
    
    public String getColourNormal() {
        return "§7";
    }
    
    public int getSelectedItem() {
        return selectedItem;
    }
    
    public int getTabHeight() {
        return tabHeight;
    }
    
    public int getTransition() {
        return transition;
    }
    
    public UnicodeFontRenderer getFontRenderer() {
        return fontRenderer;
    }
    
    @Override
    public void onEvent(Event e) {
        if (e instanceof KeyPressed) {
            KeyPressed event = (KeyPressed)e;
            switch (event.getKey()) {
                case 200: {
                    if (mainMenu) {
                        --selectedTab;
                        if (selectedTab < 0) {
                            selectedTab = tabs.size() - 1;
                        }
                        transition = 11;
                        break;
                    }
                    --selectedItem;
                    if (selectedItem < 0) {
                        selectedItem = tabs.get(selectedTab).getMods().size() - 1;
                    }
                    if (tabs.get(selectedTab).getMods().size() > 1) {
                        transition = 11;
                        break;
                    }
                    break;
                }
                case 208: {
                    if (mainMenu) {
                        ++selectedTab;
                        if (selectedTab > tabs.size() - 1) {
                            selectedTab = 0;
                        }
                        transition = -11;
                        break;
                    }
                    ++selectedItem;
                    if (selectedItem > tabs.get(selectedTab).getMods().size() - 1) {
                        selectedItem = 0;
                    }
                    if (tabs.get(selectedTab).getMods().size() > 1) {
                        transition = -11;
                        break;
                    }
                    break;
                }
                case 205: {
                	if(right == true) {
                		if (!mainMenu) {
                			mainMenu = true;
                			break;
                		}
                		break;	
                	}else if(right == false) {
                		if (mainMenu) {
                            mainMenu = false;
                            selectedItem = 0;
                            break;
                        }
                        if (!mainMenu) {
                            tabs.get(selectedTab).getMods().get(selectedItem).getModule().toggle();
                            break;
                        }
                        break;
                	}
                }
                case 203: {
                	if(right == true) {
                		if (mainMenu) {
                			mainMenu = false;
                			selectedItem = 0;
                			break;
                		}
                		if (!mainMenu) {
                			tabs.get(selectedTab).getMods().get(selectedItem).getModule().toggle();
                			break;
                		}
                		break;   		
                	}else if(right == false) {
                		if (!mainMenu) {
                			mainMenu = true;
                			break;
                		}
                		break;	
                	}
                }
                case 28: {
                    if (!mainMenu) {
                        tabs.get(selectedTab).getMods().get(selectedItem).getModule().toggle();
                        break;
                    }
                    break;
                }
            }
        }
    }
}
