package dev.stephen.nexus.module.modules.render;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class Fullbright extends Module {
    public Fullbright() {
        super("Fullbright", "Makes the game BRIGHT", 0, ModuleCategory.RENDER);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (mc.player == null || mc.world == null) {
            return;
        }
        mc.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 42069));
    };

    @Override
    public void onDisable() {
        mc.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        super.onDisable();
    }
}
