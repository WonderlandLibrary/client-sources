// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.gui.newclick;

import java.io.IOException;
import com.klintos.twelve.gui.newclick.elements.base.Element;
import java.util.Iterator;
import com.klintos.twelve.gui.newclick.elements.onetheme.ElementButton;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.Twelve;
import com.klintos.twelve.mod.ModCategory;
import java.io.Writer;
import java.nio.file.FileAlreadyExistsException;
import java.io.PrintWriter;
import java.io.FileWriter;
import com.klintos.twelve.gui.newclick.elements.onetheme.OnePanel;
import com.klintos.twelve.gui.newclick.elements.onetheme.RadarPanel;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

import com.klintos.twelve.gui.newclick.elements.base.Panel;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui extends GuiScreen
{
    private List<Panel> panels;
    private Panel top;
    private File settings;
    
    public ClickGui() {
        this.panels = new ArrayList<Panel>();
        this.settings = new File(Minecraft.getMinecraft().mcDataDir + "\\ONE2\\gui2.txt");
        this.panels.clear();
        this.loadPanels();
    }
    
    private void loadPanels() {
        try {
	        if (this.settings.exists()) {
	            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.settings));
	            String readString = "";
	            while ((readString = bufferedReader.readLine()) != null) {
	                String[] split = readString.split(":");
	                if (split[0].equals("Radar")) {
	                    RadarPanel panel = new RadarPanel(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), Boolean.parseBoolean(split[3]), Boolean.parseBoolean(split[4]), this);
	                    this.panels.add(panel);
	                    continue;
	                }
	                OnePanel panel = new OnePanel(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2]), Boolean.parseBoolean(split[3]), Boolean.parseBoolean(split[4]), this);
	                this.panels.add(panel);
	            }
		    	if (this.panels.size() > 1) {
		    		this.setPanelOnTop(this.panels.get(this.panels.size() - 1));
		    	}
		    	bufferedReader.close();
	        } else {
	        int x = 2;
	    	for (ModCategory category : ModCategory.values()) {
	          Panel panel2 = new OnePanel(String.valueOf(category.name().substring(0, 1)) + category.name().substring(1, category.name().length()).toLowerCase(), x, 2, false, false, this);
	          this.panels.add(panel2);
	          x += panel2.getWidth() + 2;    		
	    	}
	    	if (this.panels.size() > 1) {
	    		this.setPanelOnTop(this.panels.get(this.panels.size() - 1));
	    	}
	        this.panels.add(new RadarPanel("Radar", 2, this.panels.get(0).getHeight() + 4, false, false, this));
	        }
	        for (Panel panel : this.panels) {
	            int y = panel.getHeight();
	            for (Mod mod : Twelve.getInstance().getModHandler().mods) {
	                if (!mod.getModCategory().toString().equals(panel.getTitle().toUpperCase())) continue;
	                ElementButton element = new ElementButton(mod, panel.getPosX() + 2, panel.getPosY() + y, panel);
	                panel.addElement(element);
	                y += element.getHeight() + 2;
	            }
	            panel.setOpenHeight(y);
		        this.saveSettings();
	        }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void saveSettings() {
        try {
            final PrintWriter printWriter = new PrintWriter(new FileWriter(this.settings));
            for (final Panel panel : this.panels) {
                printWriter.println(String.valueOf(panel.getTitle()) + ":" + panel.getPosX() + ":" + panel.getPosY() + ":" + panel.isExpanded() + ":" + panel.isPinned());
            }
            printWriter.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (FileAlreadyExistsException e) {
        	e.printStackTrace();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
    
    public void setPanelOnTop(final Panel panel) {
        this.top = panel;
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        for (final Panel panel : this.panels) {
            panel.mouseClicked(mouseX, mouseY, button);
        }
        this.saveSettings();
        super.mouseClicked(mouseX, mouseY, button);
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float button) {
        this.drawDefaultBackground();
        if (this.panels.indexOf(this.top) != this.panels.size() - 1) {
            this.panels.remove(this.panels.indexOf(this.top));
            this.panels.add(this.top);
        }
        for (final Panel panel : this.panels) {
            panel.draw(mouseX, mouseY);
        }
    }
    
    public void drawPinned() {
        for (final Panel panel : this.panels) {
            if (panel.isPinned()) {
                panel.draw(0, 0);
            }
        }
    }
}
