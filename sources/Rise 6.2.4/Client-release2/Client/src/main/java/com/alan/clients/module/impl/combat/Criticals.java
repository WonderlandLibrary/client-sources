package com.alan.clients.module.impl.combat;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.combat.criticals.*;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.combat.criticals.name"}, description = "module.combat.criticals.description", category = Category.COMBAT)
public final class Criticals extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new PacketCriticals("Packet", this))
            .add(new EditCriticals("Edit", this))
            .add(new NoGroundCriticals("No Ground", this))
            .add(new VulcanCriticals("Vulcan", this))
            .add(new WatchdogCriticals("Watchdog", this))
            .add(new VerusCriticals("Verus", this))
            .setDefault("Packet");
}
