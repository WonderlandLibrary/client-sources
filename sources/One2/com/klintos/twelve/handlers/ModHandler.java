// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.handlers;

import java.io.File;
import java.util.Iterator;
import com.klintos.twelve.mod.untoggleable.MidClickFriends;
import com.klintos.twelve.mod.untoggleable.LagDetector;
import com.klintos.twelve.mod.untoggleable.KillFeed;
import com.klintos.twelve.mod.Zoot;
import com.klintos.twelve.mod.Velocity;
import com.klintos.twelve.mod.Tracers;
import com.klintos.twelve.mod.Step;
import com.klintos.twelve.mod.Sprint;
import com.klintos.twelve.mod.Speedmine;
import com.klintos.twelve.mod.Speed;
import com.klintos.twelve.mod.Sneak;
import com.klintos.twelve.mod.SkinDerp;
import com.klintos.twelve.mod.Search;
import com.klintos.twelve.mod.Pullback;
import com.klintos.twelve.mod.Phase;
import com.klintos.twelve.mod.NoSlowdown;
import com.klintos.twelve.mod.NoRotationSet;
import com.klintos.twelve.mod.NoFall;
import com.klintos.twelve.mod.NameTags;
import com.klintos.twelve.mod.Ladders;
import com.klintos.twelve.mod.Jesus;
import com.klintos.twelve.mod.InvMove;
import com.klintos.twelve.mod.Freecam;
import com.klintos.twelve.mod.Flight;
import com.klintos.twelve.mod.ESP;
import com.klintos.twelve.mod.Criticals;
import com.klintos.twelve.mod.ClickBlink;
import com.klintos.twelve.mod.ChestStealer;
import com.klintos.twelve.mod.ChestESP;
import com.klintos.twelve.mod.Brightness;
import com.klintos.twelve.mod.BlockOverlay;
import com.klintos.twelve.mod.Blink;
import com.klintos.twelve.mod.AutoArmor;
import com.klintos.twelve.mod.Aura;
import com.klintos.twelve.mod.AntiHunger;
import com.klintos.twelve.mod.value.Value;
import com.klintos.twelve.mod.Mod;
import java.util.ArrayList;

public class ModHandler
{
    public ArrayList<Mod> mods;
    public ArrayList<Mod> registeredMods;
    public ArrayList<Value> values;
    
    public ModHandler() {
        this.mods = new ArrayList<Mod>();
        this.registeredMods = new ArrayList<Mod>();
        this.values = new ArrayList<Value>();
        this.mods.clear();
        this.mods.add(new AntiHunger());
        this.mods.add(new Aura());
        this.mods.add(new AutoArmor());
        this.mods.add(new Blink());
        this.mods.add(new BlockOverlay());
        this.mods.add(new Brightness());
        this.mods.add(new ChestESP());
        this.mods.add(new ChestStealer());
        this.mods.add(new ClickBlink());
        this.mods.add(new Criticals());
        this.mods.add(new ESP());
        this.mods.add(new Flight());
        this.mods.add(new Freecam());
        this.mods.add(new InvMove());
        this.mods.add(new Jesus());
        this.mods.add(new Ladders());
        this.mods.add(new NameTags());
        this.mods.add(new NoFall());
        this.mods.add(new NoRotationSet());
        this.mods.add(new NoSlowdown());
        this.mods.add(new Phase());
        this.mods.add(new Pullback());
        this.mods.add(new Search());
        this.mods.add(new SkinDerp());
        this.mods.add(new Sneak());
        this.mods.add(new Speed());
        this.mods.add(new Speedmine());
        this.mods.add(new Sprint());
        this.mods.add(new Step());
        this.mods.add(new Tracers());
        this.mods.add(new Velocity());
        this.mods.add(new Zoot());
        try {
            KillFeed.class.newInstance();
            LagDetector.class.newInstance();
            MidClickFriends.class.newInstance();
        }
        catch (Exception ex) {}
    }
    
    public ArrayList<Mod> getMods() {
        return this.mods;
    }
    
    public ArrayList<Value> getValues() {
        return this.values;
    }
    
    public Mod getMod(final String ModName) {
        Mod finalMod = null;
        for (final Mod Mod : this.mods) {
            if (Mod.getModName().equalsIgnoreCase(ModName)) {
                finalMod = Mod;
            }
        }
        return finalMod;
    }
    
    public void loadMods(final String pkg) {
        try {
            final File directory = new File(ClassLoader.getSystemClassLoader().getResource(pkg.replace(".", "/")).toURI());
            if (directory != null && directory.exists()) {
                String[] list;
                for (int length = (list = directory.list()).length, i = 0; i < length; ++i) {
                    String fileName = list[i];
                    if (fileName.contains(".class")) {
                        fileName = fileName.replace(".class", "");
                        final Class clazz = Class.forName(String.valueOf(pkg) + '.' + fileName);
                        if (clazz.getSuperclass() == Mod.class) {
                            final Mod mod = (Mod) clazz.newInstance();
                            this.mods.add(mod);
                            for (final Value value : mod.getValues()) {
                                this.values.add(value);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadUntoggleable(final String pkg) {
        try {
            final File directory = new File(ClassLoader.getSystemClassLoader().getResource(pkg.replace(".", "/")).toURI());
            if (directory != null && directory.exists()) {
                String[] list;
                for (int length = (list = directory.list()).length, i = 0; i < length; ++i) {
                    String fileName = list[i];
                    if (fileName.contains(".class")) {
                        fileName = fileName.replace(".class", "");
                        final Class clazz = Class.forName(String.valueOf(pkg) + '.' + fileName);
                        clazz.newInstance();
                        System.out.println(clazz.getName());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
