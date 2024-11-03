package dev.stephen.nexus.module.modules.combat.velocity;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.mixin.accesors.KeyBindingAccessor;
import dev.stephen.nexus.module.modules.combat.Velocity;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.client.option.KeyBinding;

public class LegitVelocity extends SubMode<Velocity> {
    public LegitVelocity(String name, Velocity parentModule) {
        super(name, parentModule);
    }

    private boolean jumped;

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.hurtTime == 9 && mc.currentScreen == null && 85 > Math.random() * 100) {
            KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.jumpKey).getBoundKey(), jumped = true);
        } else if (jumped) {
            KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.jumpKey).getBoundKey(), jumped = false);
        }
    };

    @Override
    public void onDisable() {
        if (jumped) {
            KeyBinding.setKeyPressed(((KeyBindingAccessor) mc.options.jumpKey).getBoundKey(), jumped = false);
        }
        super.onDisable();
    }

    @Override
    public void onEnable() {
        jumped = false;
        super.onEnable();
    }
}
