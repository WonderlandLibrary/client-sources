/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.manager.file;

import cc.diablo.Main;
import cc.diablo.helpers.TimerUtil;
import cc.diablo.manager.file.files.ConfigFile;
import cc.diablo.manager.file.files.KeyBindsFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<CustomFile> Files = new ArrayList();
    public static TimerUtil loadTimer;

    public FileManager() {
        this.makeDirectories();
        loadTimer = new TimerUtil();
        Files.add(new ConfigFile("Config", true, true));
        Files.add(new KeyBindsFile("KeyBinds", true, true));
    }

    public void loadFiles() {
        for (CustomFile f : Files) {
            try {
                if (!f.loadOnStart()) continue;
                f.loadFile();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFiles() {
        for (CustomFile f : Files) {
            try {
                f.saveFile();
            }
            catch (Exception e) {
                e.printStackTrace();
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
        try {
            if (!Main.fileDir.exists()) {
                if (Main.fileDir.mkdir()) {
                    System.out.println("Created diablo directory!");
                } else {
                    System.out.println("Failed to create directory!");
                }
            } else {
                System.out.println("Directory already exists!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static abstract class CustomFile {
        private final File file;
        private final String name;
        private final boolean load;

        public CustomFile(String name, boolean Module2, boolean loadOnStart) {
            this.name = name;
            this.load = loadOnStart;
            this.file = new File(Main.fileDir, String.valueOf(name) + ".diablo");
            if (!this.file.exists()) {
                try {
                    this.saveFile();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public <T> T getValue(T value) {
            return null;
        }

        public File getFile() {
            return this.file;
        }

        private boolean loadOnStart() {
            return this.load;
        }

        public String getName() {
            return this.name;
        }

        public abstract void loadFile() throws IOException;

        public abstract void saveFile() throws IOException;
    }
}

