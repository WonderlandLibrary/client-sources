// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.file.files;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.IOException;
import net.andrewsnetwork.icarus.clickgui.Panel;
import net.andrewsnetwork.icarus.Icarus;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import net.andrewsnetwork.icarus.file.BasicFile;

public class GuiConfiguration extends BasicFile
{
    public GuiConfiguration() {
        super("guiconfiguration");
    }
    
    @Override
    public void saveFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
            for (final Panel panel : Icarus.getClickGUI().getPanels()) {
                writer.write(String.valueOf(panel.getTitle()) + ":" + panel.getX() + ":" + panel.getY() + ":" + panel.getOpen() + ":" + panel.isVisible());
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void loadFile() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] arguments = line.split(":");
                if (arguments.length == 5) {
                    for (final Panel panel : Icarus.getClickGUI().getPanels()) {
                        if (panel.getTitle().equalsIgnoreCase(arguments[0])) {
                            panel.setX(Integer.parseInt(arguments[1]));
                            panel.setY(Integer.parseInt(arguments[2]));
                            panel.setOpen(Boolean.parseBoolean(arguments[3]));
                            panel.setVisible(Boolean.parseBoolean(arguments[4]));
                        }
                    }
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
