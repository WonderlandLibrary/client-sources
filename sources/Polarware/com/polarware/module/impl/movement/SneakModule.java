package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.sneak.HoldSneak;
import com.polarware.module.impl.movement.sneak.NCPSneak;
import com.polarware.module.impl.movement.sneak.StandardSneak;
import com.polarware.value.impl.ModeValue;

/**
 * @author Auth
 * @since 25/06/2022
 */
@ModuleInfo(name = "module.movement.sneak.name", description = "module.movement.sneak.description", category = Category.MOVEMENT)
public class SneakModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new StandardSneak("Standard", this))
            .add(new HoldSneak("Hold", this))
            .add(new NCPSneak("NCP", this))
            .setDefault("Standard");
}