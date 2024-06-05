package me.enrythebest.reborn.cracked.mods.manager;

import me.enrythebest.reborn.cracked.util.*;
import me.enrythebest.reborn.cracked.mods.base.*;
import me.enrythebest.reborn.cracked.*;
import java.util.*;

public class ModManager
{
    private static ArrayList mods;
    public static int size;
    
    static {
        ModManager.mods = new ArrayList();
    }
    
    public ModManager() {
        this.initializeMods();
    }
    
    private void initializeMods() {
        ModManager.mods.clear();
        ModManager.mods = new ModManager$1(this);
        ModManager.size = ModManager.mods.size();
        MorbidHelper.gc();
    }
    
    public static ArrayList getMods() {
        return ModManager.mods;
    }
    
    public static ModBase findMod(final Class var0) {
        Morbid.getManager();
        for (final ModBase var2 : getMods()) {
            if (var2.getClass() == var0) {
                return var2;
            }
        }
        return null;
    }
    
    public static ModBase getMod(final String var1) {
        for (final ModBase var3 : getMods()) {
            if (var3.getName().toLowerCase().equals(var1)) {
                return var3;
            }
        }
        return null;
    }
}
