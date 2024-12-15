package com.alan.clients.module.impl.movement;

import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.module.Module;
import com.alan.clients.module.api.Category;
import com.alan.clients.module.api.ModuleInfo;
import com.alan.clients.module.impl.movement.jesus.*;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.impl.BooleanValue;
import com.alan.clients.value.impl.ModeValue;

@ModuleInfo(aliases = {"module.movement.jesus.name"}, description = "module.movement.jesus.description", category = Category.MOVEMENT)
public class Jesus extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaJesus("Vanilla", this))
            .add(new GravityJesus("Vulcan Gravity", this))
            .add(new KarhuJesus("Karhu", this))
            .add(new NCPJesus("NCP", this))
            .add(new WatchdogJesus("Watchdog", this))
            .add(new VulcanJesus("Vulcan Dolphin", this))
            .setDefault("Vanilla");

    private final BooleanValue allowJump = new BooleanValue("Allow Jump", this, true);

    @EventLink
    public final Listener<JumpEvent> onJump = event -> {
        if (!allowJump.getValue() && PlayerUtil.onLiquid()) {
            event.setCancelled();
        }
    };
}