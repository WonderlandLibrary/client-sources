package com.polarware.module.impl.ghost;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.ghost.wtap.LegitWTap;
import com.polarware.module.impl.ghost.wtap.SilentWTap;
import com.polarware.value.impl.ModeValue;
import com.polarware.value.impl.NumberValue;

/**
 * @author Alan
 * @since 29/01/2021
 */

@ModuleInfo(name = "module.ghost.wtap.name", description = "module.ghost.wtap.description", category = Category.GHOST)
public class WTapModule extends Module {
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new LegitWTap("Legit", this))
            .add(new SilentWTap("Silent", this))
            .setDefault("Legit");

    public final NumberValue chance = new NumberValue("WTap Chance", this, 100, 0, 100, 1);
}