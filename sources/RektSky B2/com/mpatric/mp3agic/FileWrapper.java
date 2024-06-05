package com.mpatric.mp3agic;

import java.nio.file.*;
import java.io.*;
import java.util.concurrent.*;

public class FileWrapper
{
    protected Path path;
    protected long length;
    protected long lastModified;
    
    protected FileWrapper() {
    }
    
    public FileWrapper(final String s) throws IOException {
        this.path = Paths.get(s, new String[0]);
        this.init();
    }
    
    public FileWrapper(final File file) throws IOException {
        if (file == null) {
            throw new NullPointerException();
        }
        this.path = Paths.get(file.getPath(), new String[0]);
        this.init();
    }
    
    public FileWrapper(final Path path) throws IOException {
        if (path == null) {
            throw new NullPointerException();
        }
        this.path = path;
        this.init();
    }
    
    private void init() throws IOException {
        if (!Files.exists(this.path, new LinkOption[0])) {
            throw new FileNotFoundException("File not found " + this.path);
        }
        if (!Files.isReadable(this.path)) {
            throw new IOException("File not readable");
        }
        this.length = Files.size(this.path);
        this.lastModified = Files.getLastModifiedTime(this.path, new LinkOption[0]).to(TimeUnit.MILLISECONDS);
    }
    
    public String getFilename() {
        return this.path.toString();
    }
    
    public long getLength() {
        return this.length;
    }
    
    public long getLastModified() {
        return this.lastModified;
    }
}
