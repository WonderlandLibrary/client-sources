package com.polarware.module.impl.movement;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.module.impl.movement.jesus.GravityJesus;
import com.polarware.module.impl.movement.jesus.KarhuJesus;
import com.polarware.module.impl.movement.jesus.NCPJesus;
import com.polarware.module.impl.movement.jesus.VanillaJesus;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.JumpEvent;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.ModeValue;

@ModuleInfo(name = "module.movement.jesus.name", description = "module.movement.jesus.description", category = Category.MOVEMENT)
public class JesusModule extends Module {

    public final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaJesus("Vanilla", this))
            .add(new GravityJesus("Gravity", this))
            .add(new KarhuJesus("Karhu", this))
            .add(new NCPJesus("NCP", this))
            .setDefault("Vanilla");

    private final BooleanValue allowJump = new BooleanValue("Allow Jump", this, true);

    @EventLink()
    public final Listener<JumpEvent> onJump = event -> {

        if (!allowJump.getValue() && PlayerUtil.onLiquid()) {
            event.setCancelled(true);
        }
    };
}