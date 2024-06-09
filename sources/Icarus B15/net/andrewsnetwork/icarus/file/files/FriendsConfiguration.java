// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.file.files;

import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import net.andrewsnetwork.icarus.Icarus;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import net.andrewsnetwork.icarus.file.BasicFile;

public class FriendsConfiguration extends BasicFile
{
    public FriendsConfiguration() {
        super("friendsconfiguration");
    }
    
    @Override
    public void loadFile() {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(this.getFile()));
            String line;
            while ((line = reader.readLine()) != null) {
                final String[] arguments = line.split(":");
                Icarus.getFriendManager().addFriend(arguments[0], arguments[1]);
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
    
    @Override
    public void saveFile() {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.getFile()));
            for (final String name : Icarus.getFriendManager().getFriends().keySet()) {
                writer.write(String.valueOf(name) + ":" + Icarus.getFriendManager().getFriends().get(name));
                writer.newLine();
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
