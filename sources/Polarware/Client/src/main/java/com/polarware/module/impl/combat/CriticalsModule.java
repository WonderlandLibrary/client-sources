package com.polarware.module.impl.combat;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.combat.criticals.*;
import com.polarware.value.impl.ModeValue;

@ModuleInfo(name = "module.combat.criticals.name", description = "module.combat.criticals.description", category = Category.COMBAT)
public final class CriticalsModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new PacketCriticals("Packet", this))
            .add(new IntaveCriticals("Intave Crits Real 2024!?!?!?", this))
            .add(new PolarCriticals("Polar", this))
            .add(new VisualCriticals("Visual", this))
            .add(new EditCriticals("Edit", this))
            .add(new NoGroundCriticals("No Ground", this))
            .add(new WatchdogCriticals("Watchdog", this))
            .setDefault("Packet");
}
