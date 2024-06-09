/*
 * Decompiled with CFR 0_122.
 */
package Monix.Mod;

import Monix.Mod.Mod;
import Monix.Mod.mods.AimBot;
import Monix.Mod.mods.AntiVelocity;
import Monix.Mod.mods.BHop;
import Monix.Mod.mods.ESP;
import Monix.Mod.mods.Fly;
import Monix.Mod.mods.FullBright;
import Monix.Mod.mods.GUI;
import Monix.Mod.mods.Glide;
import Monix.Mod.mods.Hud;
import Monix.Mod.mods.HypixelFly;
import Monix.Mod.mods.InfiniteAura;
import Monix.Mod.mods.KillAura;
import Monix.Mod.mods.LongJump;
import Monix.Mod.mods.NCPFall;
import Monix.Mod.mods.NCPSpeed;
import Monix.Mod.mods.NCPSpeed2;
import Monix.Mod.mods.NoFall;
import Monix.Mod.mods.NoSlowDown;
import Monix.Mod.mods.Notify;
import Monix.Mod.mods.Speed;
import Monix.Mod.mods.Sprint;
import java.util.ArrayList;

public class ModManager {
    public static ArrayList<Mod> activeMods = new ArrayList();

    public ModManager() {
        activeMods.add(new Fly());
        activeMods.add(new AntiVelocity());
        activeMods.add(new HypixelFly());
        activeMods.add(new FullBright());
        activeMods.add(new InfiniteAura());
        activeMods.add(new KillAura());
        activeMods.add(new LongJump());
        activeMods.add(new NoFall());
        activeMods.add(new NoSlowDown());
        activeMods.add(new Speed());
        activeMods.add(new Sprint());
        activeMods.add(new Glide());
        activeMods.add(new AimBot());
        activeMods.add(new NCPFall());
        activeMods.add(new BHop());
        activeMods.add(new NCPSpeed());
        activeMods.add(new NCPSpeed2());
        activeMods.add(new ESP());
        activeMods.add(new GUI());
        activeMods.add(new Notify());
        activeMods.add(new Hud());
    }

    public Mod getMod(Class<? extends Mod> clazz) {
        for (Mod mod : ModManager.getMods()) {
            if (mod.getClass() != clazz) continue;
            return mod;
        }
        return null;
    }

    public static void arrayListSorter(String order) {
    }

    public static ArrayList<Mod> getMods() {
        return activeMods;
    }

    public Mod getModbyName(String name) {
        for (Mod mod : ModManager.getMods()) {
            if (!mod.getName().equalsIgnoreCase(name)) continue;
            return mod;
        }
        return null;
    }

    public ArrayList<Mod> enabledMods() {
        ArrayList<Mod> emods = new ArrayList<Mod>();
        for (Mod m2 : activeMods) {
            if (m2.isToggled()) {
                emods.add(m2);
                continue;
            }
            if (m2.isToggled() || !emods.contains(m2)) continue;
            emods.remove(m2);
        }
        return emods;
    }
}

