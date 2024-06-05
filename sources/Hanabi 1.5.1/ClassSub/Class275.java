package ClassSub;

import java.util.*;
import net.minecraft.client.*;
import java.io.*;

public class Class275
{
    public static ArrayList<Class5> Files;
    private static File directory;
    
    
    public Class275() {
        this.makeDirectories();
        Class275.Files.add(new Class85("alts", false, true));
    }
    
    public void loadFiles() {
        for (final Class5 class5 : Class275.Files) {
            try {
                if (!Class5.access$000(class5)) {
                    continue;
                }
                class5.loadFile();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void saveFiles() {
        for (final Class5 class5 : Class275.Files) {
            try {
                class5.saveFile();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public Class5 getFile(final Class<? extends Class5> clazz) {
        for (final Class5 class5 : Class275.Files) {
            if (class5.getClass() != clazz) {
                continue;
            }
            return class5;
        }
        return null;
    }
    
    public void makeDirectories() {
        try {
            if (!Class275.directory.exists()) {
                if (Class275.directory.mkdir()) {
                    System.out.println("Directory is created!");
                }
                else {
                    System.out.println("Failed to create directory!");
                }
            }
        }
        catch (Exception ex) {
            throw new RuntimeException();
        }
    }
    
    static File access$100() {
        return Class275.directory;
    }
    
    static {
        Class275.Files = new ArrayList<Class5>();
        Class275.directory = new File(String.valueOf(Minecraft.getMinecraft().mcDataDir.toString()) + "\\" + "Hanabi");
    }
    
    public abstract static class Class5
    {
        private final File file;
        private final String name;
        private boolean load;
        
        
        public Class5(final String name, final boolean b, final boolean load) {
            this.name = name;
            this.load = load;
            this.file = new File(Class275.access$100(), String.valueOf(name) + ".txt");
            if (!this.file.exists()) {
                try {
                    this.saveFile();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
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
        
        static boolean access$000(final Class5 class5) {
            return class5.loadOnStart();
        }
    }
}
