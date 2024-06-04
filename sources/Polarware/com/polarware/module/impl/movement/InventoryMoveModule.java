package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.inventorymove.*;
import com.polarware.value.impl.ModeValue;

/**
 * @author Alan
 * @since 20/10/2021
 */

@ModuleInfo(name = "module.movement.inventorymove.name", description = "module.movement.inventorymove.description", category = Category.MOVEMENT)
public class InventoryMoveModule extends Module {

    private final ModeValue bypassMode = new ModeValue("Bypass Mode", this)
            .add(new NormalInventoryMove("Normal", this))
            .add(new BufferAbuseInventoryMove("Buffer Abuse", this))
            .add(new CancelInventoryMove("Cancel", this))
            .add(new WatchdogInventoryMove("Watchdog", this))
            .setDefault("Normal");
}
