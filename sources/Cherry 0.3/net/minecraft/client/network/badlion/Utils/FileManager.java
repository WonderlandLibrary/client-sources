// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Utils;

import java.io.IOException;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import java.io.File;
import java.util.ArrayList;

public class FileManager
{
    public static ArrayList<CustomFile> Files;
    private static File directory;
    private static File moduleDirectory;
    
    static {
        FileManager.Files = new ArrayList<CustomFile>();
        FileManager.directory = null;
        FileManager.moduleDirectory = null;
    }
    
    public FileManager() {
        if (Util.getOSType() == Util.EnumOS.LINUX) {
            FileManager.directory = new File(Minecraft.getMinecraft().mcDataDir, "/libraries/Badlion");
            FileManager.moduleDirectory = new File(Minecraft.getMinecraft().mcDataDir, "/libraries/Badlion");
        }
        else {
            FileManager.directory = new File(Minecraft.getMinecraft().mcDataDir + "\\libraries\\pk");
            FileManager.moduleDirectory = new File(Minecraft.getMinecraft().mcDataDir + "\\libraries\\Badlion");
        }
        this.makeDirectories();
        FileManager.Files.add(new Binds("Binds", true, true));
    }
    
    public void loadFiles() {
        for (final CustomFile f : FileManager.Files) {
            try {
                if (!f.loadOnStart()) {
                    continue;
                }
                f.loadFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void saveFiles() {
        for (final CustomFile f : FileManager.Files) {
            try {
                f.saveFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public CustomFile getFile(final Class<? extends CustomFile> clazz) {
        for (final CustomFile file : FileManager.Files) {
            if (file.getClass() == clazz) {
                return file;
            }
        }
        return null;
    }
    
    public void makeDirectories() {
        if (!FileManager.directory.exists()) {
            if (FileManager.directory.mkdir()) {
                System.out.println("Directory is created!");
            }
            else {
                System.out.println("Failed to create directory!");
            }
        }
        if (!FileManager.moduleDirectory.exists()) {
            if (FileManager.moduleDirectory.mkdir()) {
                System.out.println("Directory is created!");
            }
            else {
                System.out.println("Failed to create directory!");
            }
        }
    }
    
    public abstract static class CustomFile
    {
        private final File file;
        private final String name;
        private boolean load;
        
        public CustomFile(final String name, final boolean Module, final boolean loadOnStart) {
            this.name = name;
            this.load = loadOnStart;
            if (Module) {
                this.file = new File(FileManager.moduleDirectory, String.valueOf(name) + ".txt");
            }
            else {
                this.file = new File(FileManager.directory, String.valueOf(name) + ".txt");
            }
            if (!this.file.exists()) {
                try {
                    this.saveFile();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        public <T> T getValue(final T value) {
            return null;
        }
        
        public final File getFile() {
            return this.file;
        }
        
        private boolean loadOnStart() {
            return this.load;
        }
        
        public final String getName() {
            return this.name;
        }
        
        public abstract void loadFile() throws IOException;
        
        public abstract void saveFile() throws IOException;
    }
}
