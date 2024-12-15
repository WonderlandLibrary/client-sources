package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.movement.noslow.*;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.movement.noslow.name"}, description = "module.movement.noslow.description", category = Category.MOVEMENT)
public class NoSlow extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaNoSlow("Vanilla", this))
            .add(new NCPNoSlow("NCP", this))
            .add(new NewNCPNoSlow("New NCP", this))
            .add(new IntaveNoSlow("Intave", this))
            .add(new OldIntaveNoSlow("Old Intave", this))
            .add(new VariableNoSlow("Variable", this))
            .add(new PredictionNoSlow("Prediction", this))
            .add(new WatchdogNoSlow("Watchdog", this))
            .add(new GrimNoslow("Grim",this))
            .setDefault("Vanilla");

    public final BooleanValue foodValue = new BooleanValue("Food", this, false);
    public final BooleanValue potionValue = new BooleanValue("Potion", this, false);
    public final BooleanValue swordValue = new BooleanValue("Sword", this, false);
    public final BooleanValue bowValue = new BooleanValue("Bow", this, false);

}