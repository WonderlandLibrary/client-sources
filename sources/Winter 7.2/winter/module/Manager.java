/*
 * Decompiled with CFR 0_122.
 */
package winter.module;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import shader.PimEsp;
import winter.Client;
import winter.module.Module;
import winter.module.modules.AntiBot;
import winter.module.modules.Aura;
import winter.module.modules.AutoArmor;
import winter.module.modules.AutoPot;
import winter.module.modules.AutoSoup;
import winter.module.modules.BhopSpeed;
import winter.module.modules.Blink;
import winter.module.modules.ChestESP;
import winter.module.modules.ChestStealer;
import winter.module.modules.Criticals;
import winter.module.modules.ESP;
import winter.module.modules.Experiment;
import winter.module.modules.Fastbow;
import winter.module.modules.Flight;
import winter.module.modules.Freecam;
import winter.module.modules.Fullbright;
import winter.module.modules.Ground;
import winter.module.modules.Hotbar;
import winter.module.modules.Inventory;
import winter.module.modules.Jesus;
import winter.module.modules.LongJump;
import winter.module.modules.Machinegun;
import winter.module.modules.Magnet;
import winter.module.modules.Murder;
import winter.module.modules.NCP;
import winter.module.modules.Nametags;
import winter.module.modules.NoFall;
import winter.module.modules.NoRender;
import winter.module.modules.NoSlowdown;
import winter.module.modules.NoVoid;
import winter.module.modules.NoteBot;
import winter.module.modules.NoteTuner;
import winter.module.modules.Overlay;
import winter.module.modules.Phase;
import winter.module.modules.Ragebot;
import winter.module.modules.Scaffold;
import winter.module.modules.SongStealer;
import winter.module.modules.Speedmine;
import winter.module.modules.Spin;
import winter.module.modules.Sprint;
import winter.module.modules.Step;
import winter.module.modules.Tracers;
import winter.module.modules.Velocity;
import winter.module.modules.XRay;
import winter.module.modules.ZoomEat;
import winter.module.modules.iDrop;
import winter.module.modules.waypoint.Waypoints;
import winter.utils.render.FontUtils;

public class Manager {
    private ArrayList<Module> mods;
    public static FontUtils font;
    public static Minecraft mc;

    static {
        mc = Minecraft.getMinecraft();
    }

    public void setup() {
        this.mods = new ArrayList();
        System.out.println("Setting up Manager...");
        font = new FontUtils("aaasfsfa", 0, 19);
        this.mods.add(new NoteBot());
        this.mods.add(new NoteTuner());
        this.mods.add(new SongStealer());
        this.mods.add(new Aura());
        this.mods.add(new Sprint());
        this.mods.add(new Flight());
        this.mods.add(new Step());
        this.mods.add(new Fullbright());
        this.mods.add(new Nametags());
        this.mods.add(new Speedmine());
        this.mods.add(new Jesus());
        this.mods.add(new Phase());
        this.mods.add(new NoSlowdown());
        this.mods.add(new Velocity());
        this.mods.add(new Waypoints());
        this.mods.add(new AutoPot());
        this.mods.add(new BhopSpeed());
        this.mods.add(new Freecam());
        this.mods.add(new iDrop());
        this.mods.add(new Inventory());
        this.mods.add(new Hotbar());
        this.mods.add(new ChestStealer());
        this.mods.add(new Spin());
        this.mods.add(new Scaffold());
        this.mods.add(new PimEsp());
        this.mods.add(new NoFall());
        this.mods.add(new Blink());
        this.mods.add(new ChestESP());
        this.mods.add(new Ragebot());
        this.mods.add(new ZoomEat());
        this.mods.add(new NCP());
        this.mods.add(new Criticals());
        this.mods.add(new AutoArmor());
        this.mods.add(new ESP());
        this.mods.add(new Ground());
        this.mods.add(new XRay());
        this.mods.add(new Tracers());
        this.mods.add(new NoVoid());
        this.mods.add(new Machinegun());
        this.mods.add(new Magnet());
        this.mods.add(new LongJump());
        this.mods.add(new NoRender());
        this.mods.add(new AntiBot());
        this.mods.add(new AutoSoup());
        this.mods.add(new Experiment());
        this.mods.add(new Murder());
        this.mods.add(new Fastbow());
        this.mods.add(new Overlay());
        System.out.println("Manager setup finished!");
    }

    public ArrayList<Module> getMods() {
        return this.mods;
    }

    public List<Module> sortedMods() {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module mod : Client.getManager().getMods()) {
            mods.add(mod);
        }
        Collections.sort(mods, new Comparator<Module>(){

            @Override
            public int compare(Module mod1, Module mod2) {
                String mod1Name = String.valueOf(mod1.getName()) + mod1.getModeForArrayListLongNameLOL();
                String mod2Name = String.valueOf(mod2.getName()) + mod2.getModeForArrayListLongNameLOL();
                return Manager.font.getWidth(mod1Name) > Manager.font.getWidth(mod2Name) ? -1 : (Manager.font.getWidth(mod2Name) > Manager.font.getWidth(mod1Name) ? 1 : 0);
            }
        });
        return mods;
    }

    public Module getMod(String modName) {
        Module module = null;
        for (Module mod : this.getMods()) {
            if (!mod.getName().equalsIgnoreCase(modName)) continue;
            module = mod;
        }
        return module;
    }

    public ArrayList<Module> getModulesInCategory(Module.Category cat2) {
        ArrayList<Module> modsInCat = new ArrayList<Module>();
        for (Module mod : this.getMods()) {
            if (!mod.getCategory().equals((Object)cat2)) continue;
            modsInCat.add(mod);
        }
        return modsInCat;
    }

}

