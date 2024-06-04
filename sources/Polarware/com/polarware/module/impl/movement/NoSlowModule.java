package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.noslow.*;
import com.polarware.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */
@ModuleInfo(name = "module.movement.noslow.name", description = "module.movement.noslow.description", category = Category.MOVEMENT)
public class NoSlowModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaNoSlow("Vanilla", this))
            .add(new IntaveNoSlow("Intave", this))
            .add(new PolarNoSlow("Polar", this))
            .add(new NewNCPNoSlow("New NCP", this))
            .add(new OldIntaveNoSlow("Old Intave", this))
            .add(new VariableNoSlow("Variable", this))
            .add(new PredictionNoSlow("Prediction", this))
            .setDefault("Vanilla");
}