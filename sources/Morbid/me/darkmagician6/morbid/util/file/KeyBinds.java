package me.darkmagician6.morbid.util.file;

import net.minecraft.client.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.util.*;
import java.util.*;
import java.io.*;

public class KeyBinds
{
    private File binds;
    
    public KeyBinds() {
        MorbidWrapper.mcObj();
        this.binds = new File(Minecraft.b(), "/Morbid/binds.txt");
    }
    
    public void saveBinds() {
        if (!this.binds.exists()) {
            try {
                this.binds.createNewFile();
                this.saveBinds();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter(this.binds));
            for (final ModBase mod : Morbid.getManager().getMods()) {
                writer.write(mod.getName() + ":" + mod.getKey() + "\r\n");
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
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
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.binds.exists()) {
            try {
                final BufferedReader reader = new BufferedReader(new FileReader(this.binds));
                String s = "";
                while ((s = reader.readLine()) != null) {
                    final String[] split = s.split(":");
                    Morbid.getManager().getMod(split[0].toLowerCase()).setKey(split[1].toUpperCase());
                    System.out.println("Keybind for: " + Morbid.getManager().getMod(split[0].toLowerCase()).getName() + " to: " + Morbid.getManager().getMod(split[0].toLowerCase()).getKey());
                }
                reader.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        MorbidHelper.gc();
    }
}
