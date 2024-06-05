package ClassSub;

import java.io.*;

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
