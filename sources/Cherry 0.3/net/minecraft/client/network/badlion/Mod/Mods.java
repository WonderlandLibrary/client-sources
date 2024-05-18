// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.network.badlion.Mod;

import java.util.Iterator;
import java.util.Comparator;
import net.minecraft.client.network.badlion.Mod.Collection.Render.Gui;
import net.minecraft.client.network.badlion.Mod.Collection.Render.ESP;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.Sprint;
import net.minecraft.client.network.badlion.Mod.Collection.Other.InvMove;
import net.minecraft.client.network.badlion.Mod.Collection.Render.ChestESP;
import net.minecraft.client.network.badlion.Mod.Collection.Render.NameTags;
import net.minecraft.client.network.badlion.Mod.Collection.Combat.AutoSoup;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.Jesus;
import net.minecraft.client.network.badlion.Mod.Collection.Other.FastUse;
import net.minecraft.client.network.badlion.Mod.Collection.Other.FreeCam;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.Step;
import net.minecraft.client.network.badlion.Mod.Collection.Combat.AutoPot;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.Fly;
import net.minecraft.client.network.badlion.Mod.Collection.Combat.Criticals;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.ArisPhase;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.Bhop;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.SkipClip;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.Speed;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.NoSlowDown;
import net.minecraft.client.network.badlion.Mod.Collection.Combat.Aura;
import net.minecraft.client.network.badlion.Mod.Collection.Movement.NoFall;
import net.minecraft.client.network.badlion.Mod.Collection.Other.NoRotate;
import net.minecraft.client.network.badlion.Mod.Collection.Combat.Velocity;
import net.minecraft.client.network.badlion.Mod.Collection.Other.ChestStealer;
import java.util.ArrayList;

public class Mods
{
    private ArrayList<Mod> mods;
    
    public Mods() {
        this.mods = new ArrayList<Mod>();
        this.add(new ChestStealer());
        this.add(new Speed());
        this.add(new Velocity());
        this.add(new NoRotate());
        this.add(new Bhop());
        this.add(new NoFall());
        this.add(new Aura());
        this.add(new NoSlowDown());
        this.add(new SkipClip());
        this.add(new ArisPhase());
        this.add(new Criticals());
        this.add(new Fly());
        this.add(new AutoPot());
        this.add(new Step());
        this.add(new FreeCam());
        this.add(new FastUse());
        this.add(new Jesus());
        this.add(new AutoSoup());
        this.add(new NameTags());
        this.add(new ChestESP());
        this.add(new InvMove());
        this.add(new Sprint());
        this.add(new ESP());
        this.add(new Gui());
        this.mods.sort(new Comparator<Mod>() {
            @Override
            public int compare(final Mod o1, final Mod o2) {
                return o1.getModName().length() - o2.getModName().length();
            }
        });
        this.mods.trimToSize();
    }
    
    private void add(final Mod mod) {
        this.mods.add(mod);
    }
    
    public ArrayList<Mod> getMods() {
        this.mods.sort(new Comparator<Mod>() {
            @Override
            public int compare(final Mod o1, final Mod o2) {
                return o1.getModName().length() - o2.getModName().length();
            }
        });
        this.mods.trimToSize();
        return this.mods;
    }
    
    public Mod getMod(final Class<? extends Mod> theMod) {
        for (final Mod mod : this.getMods()) {
            if (mod.getClass() == theMod) {
                return mod;
            }
        }
        return null;
    }
    
    public Mod getMod(final String theMod) {
        for (final Mod mod : this.getMods()) {
            if (mod.getName().equalsIgnoreCase(theMod)) {
                return mod;
            }
        }
        return null;
    }
}
