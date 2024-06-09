/*
 * Decompiled with CFR 0_118.
 */
package pw.vertexcode.nemphis.module;

import java.util.ArrayList;
import java.util.List;
import pw.vertexcode.nemphis.modules.AutoArmor;
import pw.vertexcode.nemphis.modules.BedFucker;
import pw.vertexcode.nemphis.modules.ChestStealer;
import pw.vertexcode.nemphis.modules.ESP;
import pw.vertexcode.nemphis.modules.Glide;
import pw.vertexcode.nemphis.modules.Gui;
import pw.vertexcode.nemphis.modules.InventoryMove;
import pw.vertexcode.nemphis.modules.Jesus;
import pw.vertexcode.nemphis.modules.Killaura;
import pw.vertexcode.nemphis.modules.Longjump;
import pw.vertexcode.nemphis.modules.NameProtect;
import pw.vertexcode.nemphis.modules.NoBob;
import pw.vertexcode.nemphis.modules.NoClip;
import pw.vertexcode.nemphis.modules.NoFall;
import pw.vertexcode.nemphis.modules.NoSlowDown;
import pw.vertexcode.nemphis.modules.NoVelocity;
import pw.vertexcode.nemphis.modules.ScaffoldWalk;
import pw.vertexcode.nemphis.modules.Sneak;
import pw.vertexcode.nemphis.modules.Speed;
import pw.vertexcode.nemphis.modules.Sprint;
import pw.vertexcode.nemphis.modules.Teleport;
import pw.vertexcode.util.module.types.ToggleableModule;

public class ModuleManager {
    public List<ToggleableModule> mod = new ArrayList<ToggleableModule>();

    public ModuleManager() {
        this.addModule(new Sprint());
        this.addModule(new InventoryMove());
        this.addModule(new NoBob());
        this.addModule(new Sneak());
        this.addModule(new Speed());
        this.addModule(new Killaura());
        this.addModule(new ESP());
        this.addModule(new NoSlowDown());
        this.addModule(new ChestStealer());
        this.addModule(new NameProtect());
        this.addModule(new Jesus());
        this.addModule(new BedFucker());
        this.addModule(new AutoArmor());
        this.addModule(new NoVelocity());
        this.addModule(new Gui());
        this.addModule(new Teleport());
        this.addModule(new Glide());
        this.addModule(new ScaffoldWalk());
        this.addModule(new Longjump());
        this.addModule(new NoClip());
        this.addModule(new NoFall());
    }

    public void addModule(ToggleableModule toggleableModule) {
        this.mod.add(toggleableModule);
    }

    public List<ToggleableModule> getMods() {
        return this.mod;
    }

    public boolean getModuleIsEnabled(String value) {
        for (ToggleableModule mod : this.mod) {
            if (!value.equalsIgnoreCase(mod.getName()) || !mod.isEnabled()) continue;
            return true;
        }
        return false;
    }

    public ToggleableModule getModuleByName(String value) {
        for (ToggleableModule mod : this.mod) {
            if (!value.equalsIgnoreCase(mod.getName())) continue;
            return mod;
        }
        return null;
    }

    public ToggleableModule getModule(Class<?> clazz) {
        for (ToggleableModule mod : this.mod) {
            if (clazz != mod.getClass()) continue;
            return mod;
        }
        return null;
    }
}

