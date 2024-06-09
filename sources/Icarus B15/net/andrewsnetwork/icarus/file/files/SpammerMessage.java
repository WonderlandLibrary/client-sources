// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.file.files;

import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.module.modules.Spammer;
import net.andrewsnetwork.icarus.file.BasicFile;

public class SpammerMessage extends BasicFile
{
    public SpammerMessage() {
        super("spammermessage");
    }
    
    @Override
    public void loadFile() {
        try {
            final Spammer e = (Spammer)Icarus.getModuleManager().getModuleByName("spammer");
            if (e == null) {
                return;
            }
            final BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals("")) {
                    line.replaceAll("\r", "");
                    line.replaceAll("\n", "");
                    e.getMessages().add(line);
                }
            }
            reader.close();
        }
        catch (FileNotFoundException var4) {
            var4.printStackTrace();
        }
        catch (IOException var5) {
            var5.printStackTrace();
        }
    }
    
    @Override
    public void saveFile() {
        try {
            final BufferedWriter e = new BufferedWriter(new FileWriter(this.getFile()));
            e.close();
        }
        catch (IOException var2) {
            var2.printStackTrace();
        }
    }
}
