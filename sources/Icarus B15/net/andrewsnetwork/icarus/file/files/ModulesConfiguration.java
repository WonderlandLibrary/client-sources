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
import org.lwjgl.input.Keyboard;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.Icarus;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import net.andrewsnetwork.icarus.file.BasicFile;

public class ModulesConfiguration extends BasicFile
{
    public ModulesConfiguration() {
        super("modulesconfiguration");
    }
    
    @Override
    public void saveFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
            for (final Module mod : Icarus.getModuleManager().getModules()) {
                writer.write(String.valueOf(mod.getName().toLowerCase()) + ":" + mod.isEnabled() + ":" + Keyboard.getKeyName(mod.getKey()) + ":" + mod.isVisible());
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
                if (arguments.length == 4) {
                    final Module mod = Icarus.getModuleManager().getModuleByName(arguments[0]);
                    if (mod == null) {
                        continue;
                    }
                    mod.setEnabled(Boolean.parseBoolean(arguments[1]));
                    mod.setKey(Keyboard.getKeyIndex(arguments[2].toUpperCase()));
                    mod.setVisible(Boolean.parseBoolean(arguments[3]));
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
