// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.file;

import java.util.ArrayList;
import java.util.List;

import net.andrewsnetwork.altmanager.Alts;
import net.andrewsnetwork.altmanager.LastAlt;
import net.andrewsnetwork.icarus.file.files.ElementSliderConfiguration;
import net.andrewsnetwork.icarus.file.files.FriendsConfiguration;
import net.andrewsnetwork.icarus.file.files.GuiConfiguration;
import net.andrewsnetwork.icarus.file.files.ModulesConfiguration;
import net.andrewsnetwork.icarus.file.files.SpammerMessage;
import net.andrewsnetwork.icarus.file.files.ValuesConfiguration;
import net.andrewsnetwork.icarus.utilities.Logger;

public class FileManager
{
    private List<BasicFile> files;
    
    public BasicFile getFileByName(final String filename) {
        if (this.files == null || this.files.isEmpty()) {
            return null;
        }
        for (final BasicFile file : this.files) {
            if (file.getName().equalsIgnoreCase(filename)) {
                return file;
            }
        }
        return null;
    }
    
    public void setupFiles() {
        Logger.writeConsole("Setting up files...");
        (this.files = new ArrayList<BasicFile>()).add(new ValuesConfiguration());
        this.files.add(new ModulesConfiguration());
        this.files.add(new LastAlt());
        this.files.add(new Alts());
        this.files.add(new GuiConfiguration());
        this.files.add(new FriendsConfiguration());
        this.files.add(new SpammerMessage());
        this.files.add(new ElementSliderConfiguration());
    }
    
    public void loadFiles() {
        Logger.writeConsole("Loading files...");
        if (!this.files.isEmpty()) {
            for (final BasicFile file : this.files) {
                Logger.writeConsole("File " + file.getName() + " loaded.");
                file.loadFile();
            }
        }
        Logger.writeConsole("Sucessfully loaded " + this.files.size() + " files.");
    }
    
    public List<BasicFile> getFiles() {
        return this.files;
    }
}
