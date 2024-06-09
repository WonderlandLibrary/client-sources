/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import winter.utils.file.files.Modules;
import winter.utils.file.files.Options;

public class FileManager {
    public static ArrayList<CustomFile> Files = new ArrayList();
    private static File directory = null;
    private static File moduleDirectory = null;

    public FileManager() {
        if (Util.getOSType() == Util.EnumOS.LINUX) {
            directory = new File(Minecraft.getMinecraft().mcDataDir, "/Winter");
            moduleDirectory = new File(Minecraft.getMinecraft().mcDataDir, "/Winter");
        } else {
            directory = new File(Minecraft.getMinecraft().mcDataDir + "\\Winter");
            moduleDirectory = new File(Minecraft.getMinecraft().mcDataDir + "\\Winter");
        }
        this.makeDirectories();
        Files.add(new Modules("Modules", true, true));
        Files.add(new Options("Options", true, true));
    }

    public void loadFiles() {
        for (CustomFile f2 : Files) {
            try {
                if (!f2.loadOnStart()) continue;
                f2.loadFile();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void saveFiles() {
        System.out.println("saving");
        for (CustomFile f2 : Files) {
            try {
                f2.saveFile();
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public CustomFile getFile(Class<? extends CustomFile> clazz) {
        for (CustomFile file : Files) {
            if (file.getClass() != clazz) continue;
            return file;
        }
        return null;
    }

    public void makeDirectories() {
        if (!directory.exists()) {
            if (directory.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        if (!moduleDirectory.exists()) {
            if (moduleDirectory.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public static abstract class CustomFile {
        private final File file;
        private final String name;
        private boolean load;

        public CustomFile(String name, boolean Module2, boolean loadOnStart) {
            this.name = name;
            this.load = loadOnStart;
            this.file = Module2 ? new File(moduleDirectory, String.valueOf(String.valueOf(name)) + ".txt") : new File(directory, String.valueOf(String.valueOf(name)) + ".txt");
            if (!this.file.exists()) {
                try {
                    this.saveFile();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }

        public <T> T getValue(T value) {
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

