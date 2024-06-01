package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.speed.*;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.ModeValue;

/**
 * @author Patrick (implementation)
 * @since 10/19/2021
 */
@ModuleInfo(name = "module.movement.speed.name", description = "module.movement.speed.description", category = Category.MOVEMENT)
public class SpeedModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaSpeed("Vanilla", this))
            .add(new StrafeSpeed("Strafe", this))
            .add(new InteractSpeed("Interact", this))
            .add(new VulcanSpeed("Vulcan", this))
            .add(new IntaveStrafeRecode("Intave Recode", this))
            .add(new VulcanRecode("Vulcan Recode", this))
            .add(new WatchdogSpeed("Watchdog", this))
            .add(new NCPSpeed("NCP", this))
            .add(new FuncraftSpeed("Funcraft", this))
            .add(new VerusSpeed("Verus", this))
            .add(new BlocksMCSpeed("BlocksMC", this))
            .add(new MineMenClubSpeed("MineMenClub", this))
            .add(new KoksCraftSpeed("KoksCraft", this))
            .add(new LegitSpeed("Legit", this))
            .add(new PolarSpeed("Polar", this))
            .setDefault("Vanilla");

    private final BooleanValue disableOnTeleport = new BooleanValue("Disable on Teleport", this, false);
    private final BooleanValue stopOnDisable = new BooleanValue("Stop on Disable", this, false);

    @Override
    protected void onDisable() {
        mc.timer.timerSpeed = 1.0F;

        if (stopOnDisable.getValue()) {
            MoveUtil.stop();
        }
    }

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        if (disableOnTeleport.getValue()) {
            this.toggle();
        }
    };
}