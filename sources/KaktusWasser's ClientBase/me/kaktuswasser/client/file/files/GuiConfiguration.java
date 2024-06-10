// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.file.files;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.clickgui.Panel;
import me.kaktuswasser.client.file.BasicFile;

import java.io.IOException;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class GuiConfiguration extends BasicFile
{
    public GuiConfiguration() {
        super("guiconfiguration");
    }
    
    @Override
    public void saveFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
            for (final Panel panel : Client.getClickGUI().getPanels()) {
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
                    for (final Panel panel : Client.getClickGUI().getPanels()) {
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
