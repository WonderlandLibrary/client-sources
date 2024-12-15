package com.alan.clients.module.impl.combat;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.combat.mimic.ClickerMimic;
import com.alan.clients.module.impl.combat.mimic.RotationsMimic;
import com.alan.clients.value.impl.BooleanValue;

@ModuleInfo(aliases = {"Mimic"}, description = "", category = Category.COMBAT)
public final class Mimic extends Module {

    private BooleanValue rotations = new BooleanValue("Rotations", this, true, new RotationsMimic("Rotations", this));
    private BooleanValue clicker = new BooleanValue("Clicker", this, true, new ClickerMimic("Clicker", this));

}
