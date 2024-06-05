/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;

public class FileManager {
    public static File dir;
    private static final File ALT;
    private static final File LASTALT;
    private static File SPOTIFY;

    public static File getConfigFile(String name) {
        File file = new File(dir, String.format("%s.txt", name));
        if (file.exists()) return file;
        try {
            file.createNewFile();
            return file;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return file;
    }

    public static void init() {
        if (dir.exists()) return;
        dir.mkdir();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static List<String> read(String file) {
        ArrayList<String> out = new ArrayList<String>();
        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
            File f = new File(dir, file);
            File f2 = new File(SPOTIFY, file);
            if (!f.exists()) {
                f.createNewFile();
            }
            Throwable t = null;
            try {
                FileInputStream fis = new FileInputStream(f);
                try {
                    InputStreamReader isr = new InputStreamReader(fis);
                    try {
                        try (BufferedReader br = new BufferedReader(isr);){
                            String line = "";
                            while ((line = br.readLine()) != null) {
                                out.add(line);
                            }
                        }
                        if (isr != null) {
                            isr.close();
                        }
                    }
                    finally {
                        Throwable t2;
                        if (t == null) {
                            t = t2 = null;
                        } else {
                            t2 = null;
                            if (t != t2) {
                                t.addSuppressed(t2);
                            }
                        }
                        if (isr != null) {
                            isr.close();
                        }
                    }
                    if (fis == null) return out;
                    fis.close();
                    ArrayList<String> arrayList = out;
                    return arrayList;
                }
                finally {
                    Throwable t3;
                    if (t == null) {
                        t = t3 = null;
                    } else {
                        t3 = null;
                        if (t != t3) {
                            t.addSuppressed(t3);
                        }
                    }
                    if (fis != null) {
                        fis.close();
                    }
                }
            }
            finally {
                Throwable t4;
                if (t == null) {
                    t = t4 = null;
                } else {
                    t4 = null;
                    if (t != t4) {
                        t.addSuppressed(t4);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void save(String file, String content, boolean append) {
        try {
            File f = new File(dir, file);
            if (!f.exists()) {
                f.createNewFile();
            }
            Throwable t = null;
            try (FileWriter writer = new FileWriter(f, append);){
                writer.write(content);
                return;
            }
            finally {
                Throwable t2;
                if (t == null) {
                    t = t2 = null;
                } else {
                    t2 = null;
                    if (t != t2) {
                        t.addSuppressed(t2);
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        File mcDataDir = Minecraft.getMinecraft().mcDataDir;
        dir = new File(mcDataDir, "DrunkClient");
        ALT = FileManager.getConfigFile("Alts");
        LASTALT = FileManager.getConfigFile("LastAlt");
    }
}

