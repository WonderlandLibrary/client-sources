// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.file.files;

import java.io.Writer;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.file.BasicFile;
import me.kaktuswasser.client.module.modules.Spammer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;

public class SpammerMessage extends BasicFile
{
    public SpammerMessage() {
        super("spammermessage");
    }
    
    @Override
    public void loadFile() {
        try {
            final Spammer e = (Spammer)Client.getModuleManager().getModuleByName("spammer");
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
