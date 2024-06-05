package me.darkmagician6.morbid.mods.manager;

import net.minecraft.client.*;
import me.darkmagician6.morbid.mods.*;
import java.util.*;
import java.io.*;

public class FileManager
{
    public static boolean Attivo;
    public File MorbidDirectory;
    
    public FileManager() {
        this.MorbidDirectory = new File(Minecraft.b(), "/Morbid/");
        if (!this.MorbidDirectory.exists()) {
            this.MorbidDirectory.mkdirs();
        }
    }
    
    public void saveXrayList() {
        try {
            final File file = new File(this.MorbidDirectory.getAbsolutePath(), "xray.txt");
            final BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (final int i : Xray.blocks) {
                out.write(i + "\r\n");
            }
            out.close();
        }
        catch (Exception ex) {}
    }
    
    public void loadXrayList() {
        try {
            final File file = new File(this.MorbidDirectory.getAbsolutePath(), "xray.txt");
            final FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            final DataInputStream in = new DataInputStream(fstream);
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                final String curLine = line.toLowerCase().trim();
                final int id = Integer.parseInt(curLine);
                Xray.blocks.add(id);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            this.saveXrayList();
        }
    }
    
    static {
        FileManager.Attivo = false;
    }
}
