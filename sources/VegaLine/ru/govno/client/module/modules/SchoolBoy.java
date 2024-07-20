/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.module.modules;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import ru.govno.client.module.Module;
import ru.govno.client.module.settings.Settings;

public class SchoolBoy
extends Module {
    public static SchoolBoy get;
    public Settings Targets;
    public Settings ChangeFirstHeight;

    public SchoolBoy() {
        super("SchoolBoy", 0, Module.Category.RENDER);
        get = this;
        this.Targets = new Settings("Targets", "Self", (Module)this, new String[]{"Self", "Friends", "Self&Friends", "All", "Self&All", "FullAll"});
        this.settings.add(this.Targets);
        this.ChangeFirstHeight = new Settings("ChangeFirstHeight", true, (Module)this);
        this.settings.add(this.ChangeFirstHeight);
    }

    public static boolean isSetEyeHeightReduce(Entity forEntity) {
        String targets = get != null ? SchoolBoy.get.Targets.currentMode : null;
        return get != null && SchoolBoy.get.actived && SchoolBoy.get.ChangeFirstHeight.bValue && forEntity instanceof EntityPlayerSP && (targets.equalsIgnoreCase("Self&All") || targets.equalsIgnoreCase("Self") || targets.equalsIgnoreCase("Self&Friends"));
    }
}

