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
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementValueSlider;
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementModuleButton;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import net.andrewsnetwork.icarus.file.BasicFile;

public class ElementSliderConfiguration extends BasicFile
{
    public ElementSliderConfiguration() {
        super("elementsliderconfiguration");
    }
    
    @Override
    public void saveFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
            for (final ElementValueSlider slider : ElementModuleButton.sliders) {
                if (slider == null) {
                    continue;
                }
                writer.write(String.valueOf(slider.getName()) + ":" + slider.getSlideX());
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
                if (arguments.length == 2) {
                    for (final ElementValueSlider slider : ElementModuleButton.sliders) {
                        if (slider == null) {
                            continue;
                        }
                        if (!arguments[0].equalsIgnoreCase(slider.getName())) {
                            continue;
                        }
                        slider.setSlideX(Integer.parseInt(arguments[1]));
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
