package me.darkmagician6.morbid.mods.manager;

import me.darkmagician6.morbid.mods.*;
import me.darkmagician6.morbid.util.*;
import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;
import java.util.*;

public class ModManager
{
    private ArrayList mods;
    public static int size;
    
    public ModManager() {
        this.mods = new ArrayList();
        this.initializeMods();
    }
    
    private void initializeMods() {
        this.mods.clear();
        this.mods = new ArrayList() {
            final ModManager this$0 = ModManager.this;
            
            {
                this.add(new KillAura());
                this.add(new AutoSoup());
                this.add(new AutoBlock());
                this.add(new Brightness());
                this.add(new BowAimbot());
                this.add(new AutoSword());
                this.add(new NoPush());
                this.add(new Vanilla());
                this.add(new JumpCrits());
                this.add(new Criticals());
                this.add(new Sprint());
                this.add(new ChestESP());
                this.add(new Freecam());
                this.add(new Tracers());
                this.add(new AutoRespawn());
                this.add(new Search());
                this.add(new Spider());
                this.add(new Parkour());
                this.add(new HeadInBody());
                this.add(new Xray());
                this.add(new Regen());
                this.add(new Trajectories());
                this.add(new FastBow());
                this.add(new God());
                this.add(new Fly());
                this.add(new AmericaAutoarmor());
            }
        };
        ModManager.size = this.mods.size();
        MorbidHelper.gc();
    }
    
    public ArrayList getMods() {
        return this.mods;
    }
    
    public static ModBase findMod(final Class clazz) {
        for (final ModBase xmod : Morbid.getManager().getMods()) {
            if (xmod.getClass() == clazz) {
                return xmod;
            }
        }
        return null;
    }
    
    public ModBase getMod(final String name) {
        for (final ModBase mod : this.getMods()) {
            if (mod.getName().toLowerCase().equals(name)) {
                return mod;
            }
        }
        return null;
    }
}
