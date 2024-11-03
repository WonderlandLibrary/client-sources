package dev.stephen.nexus.module.modules.combat.velocity;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.mixin.accesors.KeyBindingAccessor;
import dev.stephen.nexus.module.modules.combat.Velocity;
import dev.stephen.nexus.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.hit.HitResult;

public class IntaveVelocity extends SubMode<Velocity> {
    public IntaveVelocity(String name, Velocity parentModule) {
        super(name, parentModule);
    }

    private boolean jumped, attacked;

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
        if (mc.player.handSwingTicks != 0) {
            attacked = true;
        }

        if (mc.crosshairTarget.getType().equals(HitResult.Type.ENTITY) && mc.player.hurtTime > 0 && !attacked) {
            mc.player.getVelocity().x *= 0.6D;
            mc.player.getVelocity().z *= 0.6D;
            mc.player.setSprinting(false);
        }

        attacked = false;
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
        attacked = false;
        super.onEnable();
    }
}
