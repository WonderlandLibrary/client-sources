package com.alan.clients.module.impl.movement;

import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.movement.inventorymove.BufferAbuseInventoryMove;
import com.alan.clients.module.impl.movement.inventorymove.CancelInventoryMove;
import com.alan.clients.module.impl.movement.inventorymove.NormalInventoryMove;
import com.alan.clients.module.impl.movement.inventorymove.WatchdogInventoryMove;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.movement.inventorymove.name"}, description = "module.movement.inventorymove.description", category = Category.MOVEMENT)
public class InventoryMove extends Module {

    private final ModeValue mode = new ModeValue("Bypass Mode", this)
            .add(new NormalInventoryMove("Normal", this))
            .add(new BufferAbuseInventoryMove("Buffer Abuse", this))
            .add(new CancelInventoryMove("Cancel", this))
            .add(new WatchdogInventoryMove("Watchdog", this))
            .setDefault("Normal");
}
