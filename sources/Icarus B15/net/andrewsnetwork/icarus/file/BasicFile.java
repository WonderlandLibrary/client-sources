// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.file;

import net.andrewsnetwork.icarus.Icarus;
import java.io.File;

public abstract class BasicFile
{
    private final File file;
    private final String name;
    
    public BasicFile(final String name) {
        this.name = name;
        this.file = new File(Icarus.getDirectory(), "." + name);
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
