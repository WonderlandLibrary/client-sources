// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

import java.util.Iterator;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import net.minecraft.client.network.badlion.Mod.Mod;
import org.lwjgl.input.Keyboard;
import net.minecraft.Badlion;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;

public class Binds extends FileManager.CustomFile
{
    public Binds(final String name, final boolean Module, final boolean loadOnStart) {
        super(name, Module, loadOnStart);
    }
    
    @Override
    public void loadFile() throws IOException {
        final BufferedReader variable9 = new BufferedReader(new FileReader(this.getFile()));
        String line;
        while ((line = variable9.readLine()) != null) {
            final int i = line.indexOf(":");
            if (i >= 0) {
                final String module = line.substring(0, i).trim();
                final String key = line.substring(i + 1).trim();
                final Mod m = Badlion.getWinter().theMods.getMod(module);
                if (key.isEmpty() || m == null) {
                    continue;
                }
                m.setBind(Keyboard.getKeyIndex(key.toUpperCase()));
            }
        }
        variable9.close();
        System.out.println("Loaded " + this.getName() + " File!");
    }
    
    @Override
    public void saveFile() throws IOException {
        final PrintWriter variable9 = new PrintWriter(new FileWriter(this.getFile()));
        for (final Mod m : Badlion.getWinter().theMods.getMods()) {
            variable9.println(String.valueOf(m.getName()) + ":" + Keyboard.getKeyName(m.getBind()));
        }
        variable9.close();
    }
}
