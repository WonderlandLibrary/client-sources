package com.polarware.module.impl.combat;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.combat.regen.GrimRegen;
import com.polarware.module.impl.combat.regen.VanillaRegen;
import com.polarware.module.impl.combat.regen.WorldGuardRegen;
import com.polarware.value.impl.ModeValue;

@ModuleInfo(name = "module.combat.regen.name", description = "module.combat.regen.description", category = Category.COMBAT)
public final class RegenModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaRegen("Vanilla", this))
            .add(new WorldGuardRegen("World Guard", this))
            .add(new GrimRegen("Grim", this))
            .setDefault("Vanilla");
}
