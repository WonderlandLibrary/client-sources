package me.enrythebest.reborn.cracked.mods.manager;

import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.mods.*;
import java.util.*;
import java.io.*;

public class FileManager
{
    public static boolean Attivo;
    public File RebornDirectory;
    
    static {
        FileManager.Attivo = false;
    }
    
    public FileManager() {
        this.RebornDirectory = new File(Minecraft.getMinecraftDir(), "/Reborn/");
        if (!this.RebornDirectory.exists()) {
            this.RebornDirectory.mkdirs();
        }
    }
    
    public void saveXrayList() {
        try {
            final File var1 = new File(this.RebornDirectory.getAbsolutePath(), "xray.txt");
            final BufferedWriter var2 = new BufferedWriter(new FileWriter(var1));
            for (final int var4 : Xray.blocks) {
                var2.write(String.valueOf(var4) + "\r\n");
            }
            var2.close();
        }
        catch (Exception ex) {}
    }
    
    public void loadXrayList() {
        try {
            final File var1 = new File(this.RebornDirectory.getAbsolutePath(), "xray.txt");
            final FileInputStream var2 = new FileInputStream(var1.getAbsolutePath());
            final DataInputStream var3 = new DataInputStream(var2);
            final BufferedReader var4 = new BufferedReader(new InputStreamReader(var3));
            String var5;
            while ((var5 = var4.readLine()) != null) {
                final String var6 = var5.toLowerCase().trim();
                final int var7 = Integer.parseInt(var6);
                Xray.blocks.add(var7);
            }
        }
        catch (Exception var8) {
            var8.printStackTrace();
            this.saveXrayList();
        }
    }
}
