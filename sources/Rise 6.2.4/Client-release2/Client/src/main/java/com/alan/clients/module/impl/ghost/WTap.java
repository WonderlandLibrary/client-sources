package com.alan.clients.module.impl.ghost;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.ghost.wtap.LegitWTap;
import com.alan.clients.module.impl.ghost.wtap.SilentWTap;
import com.alan.clients.value.impl.ModeValue;
import com.alan.clients.value.impl.NumberValue;

@ModuleInfo(aliases = {"module.ghost.wtap.name", "Extra Knock Back", "Super Knock Back", "Knock Back", "Sprint Reset"}, description = "module.ghost.wtap.description", category = Category.GHOST)
public class WTap extends Module {
    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new LegitWTap("Legit", this))
            .add(new SilentWTap("Silent", this))
            .setDefault("Legit");

    public final NumberValue chance = new NumberValue("WTap Chance", this, 100, 0, 100, 1);
}