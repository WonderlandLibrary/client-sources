package me.enrythebest.reborn.cracked.util.file;

import net.minecraft.client.*;
import me.enrythebest.reborn.cracked.*;
import me.enrythebest.reborn.cracked.mods.manager.*;
import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.util.*;
import java.util.*;
import java.io.*;

public class KeyBinds
{
    private File binds;
    
    public KeyBinds() {
        MorbidWrapper.mcObj();
        this.binds = new File(Minecraft.getMinecraftDir(), "/Morbid/binds.txt");
    }
    
    public void saveBinds() {
        if (!this.binds.exists()) {
            try {
                this.binds.createNewFile();
                this.saveBinds();
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        try {
            final BufferedWriter var5 = new BufferedWriter(new FileWriter(this.binds));
            Morbid.getManager();
            for (final ModBase var7 : ModManager.getMods()) {
                var5.write(String.valueOf(var7.getName()) + ":" + var7.getKey() + "\r\n");
            }
            var5.close();
        }
        catch (Exception var8) {
            var8.printStackTrace();
        }
        MorbidHelper.gc();
    }
    
    public void loadBinds() {
        if (!this.binds.exists()) {
            try {
                this.binds.createNewFile();
                this.saveBinds();
                this.loadBinds();
            }
            catch (Exception var4) {
                var4.printStackTrace();
            }
        }
        if (this.binds.exists()) {
            try {
                final BufferedReader var5 = new BufferedReader(new FileReader(this.binds));
                String var6 = "";
                while ((var6 = var5.readLine()) != null) {
                    final String[] var7 = var6.split(":");
                    Morbid.getManager();
                    ModManager.getMod(var7[0].toLowerCase()).setKey(var7[1].toUpperCase());
                    final PrintStream out = System.out;
                    final StringBuilder sb = new StringBuilder("Keybind for: ");
                    Morbid.getManager();
                    final StringBuilder append = sb.append(ModManager.getMod(var7[0].toLowerCase()).getName()).append(" to: ");
                    Morbid.getManager();
                    out.println(append.append(ModManager.getMod(var7[0].toLowerCase()).getKey()).toString());
                }
                var5.close();
            }
            catch (Exception var8) {
                var8.printStackTrace();
            }
        }
        MorbidHelper.gc();
    }
}
