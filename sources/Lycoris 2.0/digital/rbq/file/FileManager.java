/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class FileManager {
    public final List<File> files = new ArrayList<File>();
    public static final File HOME_DIRECTORY = new File("Autumn");
    private final List<File> directoryList;

    public FileManager() {
        this.files.add(new File(HOME_DIRECTORY, "ModuleData.json"));
        this.directoryList = new ArrayList<File>(Arrays.asList(new File(HOME_DIRECTORY, "Configs")));
        HOME_DIRECTORY.mkdirs();
        for (File directory : this.directoryList) {
            if (directory.exists() || directory.mkdirs()) continue;
            throw new RuntimeException("Failed to create directory: " + directory.getName());
        }
        for (File f : this.files) {
            try {
                if (f.exists() || f.createNewFile()) continue;
                throw new IOException("Failed to create file: " + f.getName());
            }
            catch (IOException iOException) {
            }
        }
    }

    public final String read(File file) {
        StringBuilder sb = new StringBuilder();
        if (file.exists()) {
            try (Stream<String> lines = Files.lines(file.toPath(), StandardCharsets.UTF_8);){
                Iterator it = lines.iterator();
                while (it.hasNext()) {
                    sb.append((String)it.next()).append(System.lineSeparator());
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return sb.toString();
    }

    public final void write(File file, String content) {
        if (file.exists()) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(content);
                writer.close();
            }
            catch (IOException iOException) {}
        } else {
            throw new NullPointerException("File " + file.getName() + " does not exist!");
        }
    }

    public File getFile(String name) {
        for (File f : this.files) {
            if (!f.getName().equalsIgnoreCase(name)) continue;
            return f;
        }
        throw new NullPointerException("Cannot find file!");
    }

    public File getDirectory(String name) {
        for (File f : this.directoryList) {
            if (!f.getName().equalsIgnoreCase(name)) continue;
            return f;
        }
        throw new NullPointerException("Cannot find directory!");
    }
}

