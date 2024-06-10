// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.file;

import java.io.File;

import me.kaktuswasser.client.Client;

public abstract class BasicFile
{
    private final File file;
    private final String name;
    
    public BasicFile(final String name) {
        this.name = name;
        this.file = new File(Client.getDirectory(), "." + name);
        if (!this.file.exists()) {
            this.saveFile();
        }
    }
    
    public abstract void saveFile();
    
    public abstract void loadFile();
    
    public final String getName() {
        return this.name;
    }
    
    public final File getFile() {
        return this.file;
    }
}
