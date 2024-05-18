// 
// Decompiled by Procyon v0.5.30
// 

package org.reflections.vfs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

public class ZipFile implements Vfs.File
{
    private final ZipDir root;
    private final ZipEntry entry;
    
    public ZipFile(final ZipDir root, final ZipEntry entry) {
        this.root = root;
        this.entry = entry;
    }
    
    public String getName() {
        final String name = this.entry.getName();
        return name.substring(name.lastIndexOf("/") + 1);
    }
    
    public String getRelativePath() {
        return this.entry.getName();
    }
    
    public InputStream openInputStream() throws IOException {
        return this.root.jarFile.getInputStream(this.entry);
    }
    
    public String toString() {
        return this.root.getPath() + "!" + java.io.File.separatorChar + this.entry.toString();
    }
}
