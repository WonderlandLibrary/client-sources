package com.polarware.module.impl.render;

import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.other.AttackEvent;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;


/**
 * @author Alan
 * @since 28/05/2022
 */

@ModuleInfo(name = "module.render.particles.name", description = "module.render.particles.description", category = Category.RENDER)
public final class ParticlesModule extends Module {

    private final NumberValue multiplier = new NumberValue("Multiplier", this, 1, 1, 10, 1);
    private final BooleanValue alwaysCrit = new BooleanValue("Always Crit", this, true);

    private final BooleanValue alwaysSharpness = new BooleanValue("Always Sharpness", this, true);

    @EventLink()
    public final Listener<AttackEvent> onAttack = event -> {
        Entity target = event.getTarget();

        if (mc.thePlayer.fallDistance > 0 || alwaysCrit.getValue() || alwaysSharpness.getValue()) {
            for (int i = 0; i <= multiplier.getValue().intValue(); i++) {
                if (this.alwaysCrit.getValue()) {
                    mc.thePlayer.onCriticalHit(target);
                }

                if (this.alwaysSharpness.getValue()) {
                    this.mc.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC);
                }
            }
        }
    };
}